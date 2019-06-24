package uk.ac.ebi.intact.search.interactor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactor.service.InteractorIndexService;
import uk.ac.ebi.intact.search.interactor.service.InteractorSearchService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Elisabet Barrera
 */

@RestController
public class SearchInteractorController {

    private InteractorIndexService interactorIndexService;
    private InteractorSearchService interactorSearchService;

    @Autowired
    public SearchInteractorController(InteractorIndexService interactorIndexService,
                                      InteractorSearchService interactorSearchService) {
        this.interactorIndexService = interactorIndexService;
        this.interactorSearchService = interactorSearchService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAll",
            produces = {APPLICATION_JSON_VALUE})
    public List<SearchInteractor> getAllDocs() {
        List<SearchInteractor> documents = new ArrayList<>();
        // iterate all documents and add it to list
        for (SearchInteractor doc : this.interactorSearchService.findAll()) {
            documents.add(doc);
        }
        return documents;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAllTaxIdFacets", params = {"page", "size"},
            produces = {APPLICATION_JSON_VALUE} )
    public FacetPage<SearchInteractor> getAllDocsTaxIdFacets(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return this.interactorSearchService.getTaxIdFacets(page, size);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getSpeciesAndInteractorTypeFacets",
            params = {
                "query",
                "speciesFilter",
                "interactorTypeFilter",
                "page",
                "pageSize"
            },
            produces = {APPLICATION_JSON_VALUE})
    public FacetPage<SearchInteractor> getSpeciesAndInteractorTypeFacets(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "speciesFilter", required = false) Set<String> speciesFilter,
            @RequestParam(value = "interactorTypeFilter", required = false) Set<String> interactorTypeFilter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return this.interactorSearchService.getSpeciesAndInteractorTypeFacets(query, speciesFilter, interactorTypeFilter, page, pageSize);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/findInteractorWithFacet",
            params = {
                    "query",
                    "page",
                    "pageSize"
            },
            produces = {APPLICATION_JSON_VALUE})
    public SearchInteractorResult findInteractorWithFacet(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "speciesFilter", required = false) Set<String> speciesFilter,
            @RequestParam(value = "interactorTypeFilter", required = false) Set<String> interactorTypeFilter,
            @RequestParam(value = "detectionMethodFilter", required = false) Set<String> detectionMethodFilter,
            @RequestParam(value = "interactionTypeFilter", required = false) Set<String> interactionTypeFilter,
            @RequestParam(value = "interactionHostOrganismFilter", required = false) Set<String> interactionHostOrganismFilter,
            @RequestParam(value = "isNegativeFilter", required = false) boolean isNegativeFilter,
            @RequestParam(value = "minMiScore", defaultValue = "0", required = false) double minMiScore,
            @RequestParam(value = "maxMiScore", defaultValue = "1", required = false) double maxMiScore,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        FacetPage<SearchInteractor> interactorResult = interactorSearchService.findInteractorWithFacet(query, speciesFilter, interactorTypeFilter,
                detectionMethodFilter, interactionTypeFilter, interactionHostOrganismFilter,
                isNegativeFilter, minMiScore, maxMiScore, page, pageSize);

        for (SearchInteractor searchInteractor : interactorResult.getContent()) {

            // TODO: Please change the URI FROM THE URL for the interaction web service in production
            String URL = "http://ves-hx-47:8082/intact/ws/interaction/countInteractionResult?query={query}&interactorAc={interactorAc}" +
                    "&speciesFilter={speciesFilter}&interactorTypeFilter={interactorTypeFilter}&detectionMethodFilter={detectionMethodFilter}" +
                    "&interactionTypeFilter={interactionTypeFilter}&hostOrganismFilter={hostOrganismFilter}&isNegativeFilter={isNegativeFilter}" +
                    "&minMiscore={minMiscore}&maxMiscore={maxMiscore}";

            String term = searchInteractor.getInteractorId();


            RestTemplate restTemplate = new RestTemplate();
            Long interactionCount = restTemplate.getForObject(URL, Long.class, query, term,
                    speciesFilter != null ? String.join(",", speciesFilter): "",
                    interactorTypeFilter != null ? String.join(",", interactorTypeFilter): "",
                    detectionMethodFilter != null ? String.join(",", detectionMethodFilter): "",
                    interactionTypeFilter != null ? String.join(",", interactionTypeFilter): "",
                    interactionHostOrganismFilter != null ? String.join(",", interactionHostOrganismFilter) : "",
                    isNegativeFilter, minMiScore, maxMiScore);

            searchInteractor.setInteractionSearchCount(interactionCount);
        }

        return new SearchInteractorResult(interactorResult);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/datatables/{query}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getInteractorsDatatablesHandler(@PathVariable String query,
                                                                   HttpServletRequest request) throws IOException {
        Set<String> interactorTypeFilter = new HashSet<>();
        Set<String> speciesFilter = new HashSet<>();
        Set<String> interactionTypeFilter = new HashSet<>();
        Set<String> detectionMethodFilter = new HashSet<>();
        Set<String> hostOrganismFilter = new HashSet<>();

        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        if (request.getParameterValues("interactorType[]") != null) {
            interactorTypeFilter = new HashSet<>(Arrays.asList(request.getParameterValues("interactorType[]")));
        }
        if (request.getParameterValues("species[]") != null) {
            speciesFilter = new HashSet<>(Arrays.asList(request.getParameterValues("species[]")));
        }
        if (request.getParameterValues("interactionType[]") != null) {
            interactionTypeFilter = new HashSet<>(Arrays.asList(request.getParameterValues("interactionType[]")));
        }
        if (request.getParameterValues("detectionMethod[]") != null) {
            detectionMethodFilter = new HashSet<>(Arrays.asList(request.getParameterValues("detectionMethod[]")));
        }
        if (request.getParameterValues("hostOrganism[]") != null) {
            hostOrganismFilter = new HashSet<>(Arrays.asList(request.getParameterValues("hostOrganism[]")));
        }

        boolean negativeFilter = Boolean.parseBoolean(request.getParameter("negativeInteraction"));
        double minMiScoreFilter = Double.parseDouble(request.getParameter("miScoreMin"));
        double maxMiScoreFilter = Double.parseDouble(request.getParameter("miScoreMax"));

        FacetPage<SearchInteractor> searchInteractors = interactorSearchService.findInteractorWithFacet(query, speciesFilter,
                interactorTypeFilter, detectionMethodFilter, interactionTypeFilter, hostOrganismFilter, negativeFilter,
                minMiScoreFilter, maxMiScoreFilter, page, pageSize);

        for (SearchInteractor searchInteractor : searchInteractors.getContent()) {

            // TODO: Please change the URI FROM THE URL for the interaction web service in production
            String URL = "http://ves-hx-47:8082/intact/ws/interaction/countInteractionResult?query={query}&interactorAc={interactorAc}" +
                    "&speciesFilter={speciesFilter}&interactorTypeFilter={interactorTypeFilter}&detectionMethodFilter={detectionMethodFilter}" +
                    "&interactionTypeFilter={interactionTypeFilter}&hostOrganismFilter={hostOrganismFilter}&isNegativeFilter={isNegativeFilter}" +
                    "&minMiscore={minMiscore}&maxMiscore={maxMiscore}";

            String term = searchInteractor.getInteractorId();


            RestTemplate restTemplate = new RestTemplate();
            Long interactionCount = restTemplate.getForObject(URL, Long.class, query, term,
                    speciesFilter != null ? String.join(",", speciesFilter): "",
                    interactorTypeFilter != null ? String.join(",", interactorTypeFilter): "",
                    detectionMethodFilter != null ? String.join(",", detectionMethodFilter): "",
                    interactionTypeFilter != null ? String.join(",", interactionTypeFilter): "",
                    hostOrganismFilter != null ? String.join(",", hostOrganismFilter) : "",
                    negativeFilter, minMiScoreFilter, maxMiScoreFilter);

            searchInteractor.setInteractionSearchCount(interactionCount);
        }

        SearchInteractorResult searchInteractorResult = new SearchInteractorResult(searchInteractors);


        JSONObject result = new JSONObject();
        result.put("draw", request.getParameter("draw"));
        result.put("recordsTotal", searchInteractorResult.getTotalElements());
        result.put("recordsFiltered", searchInteractorResult.getTotalElements());

        JSONArray data = new JSONArray();

        for (SearchInteractor interactor : searchInteractorResult.getContent()) {
            StringWriter writer = new StringWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, interactor);
            data.add(writer);
        }

        result.put("data", data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", APPLICATION_JSON_VALUE);
        headers.add("X-Clacks-Overhead", "headers");

        return new ResponseEntity<>(result.toString(), headers, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/findInteractor/{query}",
            produces = {APPLICATION_JSON_VALUE})
    public Page<SearchInteractor> findInteractor(@PathVariable String query) {
        return this.interactorSearchService.findInteractor(query);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/countTotal",
            produces = {APPLICATION_JSON_VALUE})
    public long countTotal() {
        return this.interactorSearchService.countTotal();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/graph/findInteractorForGraphJson",
            params = {
                    "query",
                    "page",
                    "pageSize"
            })
    public Page<SearchInteractor> findInteractorForGraphJson(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "speciesFilter", required = false) Set<String> speciesFilter,
            @RequestParam(value = "interactorTypeFilter", required = false) Set<String> interactorTypeFilter,
            @RequestParam(value = "detectionMethodFilter", required = false) Set<String> detectionMethodFilter,
            @RequestParam(value = "interactionTypeFilter", required = false) Set<String> interactionTypeFilter,
            @RequestParam(value = "interactionHostOrganismFilter", required = false) Set<String> interactionHostOrganismFilter,
            @RequestParam(value = "isNegativeFilter", required = false) boolean isNegativeFilter,
            @RequestParam(value = "minMiScore", defaultValue = "0", required = false) double minMiScore,
            @RequestParam(value = "maxMiScore", defaultValue = "1", required = false) double maxMiScore,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = Integer.MAX_VALUE + "") int pageSize) {

        return this.interactorSearchService.findInteractorForGraphJson(query, speciesFilter, interactorTypeFilter,
                detectionMethodFilter, interactionTypeFilter, interactionHostOrganismFilter,
                isNegativeFilter, minMiScore, maxMiScore, page, pageSize);
    }
}
