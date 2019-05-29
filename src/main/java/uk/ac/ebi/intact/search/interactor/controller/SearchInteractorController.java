package uk.ac.ebi.intact.search.interactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactor.service.InteractorIndexService;
import uk.ac.ebi.intact.search.interactor.service.InteractorSearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @GetMapping(value = "/interactor/getAll",
            produces = {APPLICATION_JSON_VALUE})
    public List<SearchInteractor> getAllDocs() {
        List<SearchInteractor> documents = new ArrayList<>();
        // iterate all documents and add it to list
        for (SearchInteractor doc : this.interactorSearchService.findAll()) {
            documents.add(doc);
        }
        return documents;
    }

    @GetMapping(value = "/interactor/getAllTaxIdFacets", params = {"page", "size"},
            produces = {APPLICATION_JSON_VALUE} )
    public FacetPage<SearchInteractor> getAllDocsTaxIdFacets(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return this.interactorSearchService.getTaxIdFacets(page, size);
    }

    @RequestMapping(value = "/interactor/getSpeciesAndInteractorTypeFacets",
            params = {
                "query",
                "speciesFilter",
                "interactorTypeFilter",
                "page",
                "pageSize"
            },
            method = RequestMethod.GET,
            produces = {APPLICATION_JSON_VALUE})
    public FacetPage<SearchInteractor> getSpeciesAndInteractorTypeFacets(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "speciesFilter", required = false) Set<String> speciesFilter,
            @RequestParam(value = "interactorTypeFilter", required = false) Set<String> interactorTypeFilter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return this.interactorSearchService.getSpeciesAndInteractorTypeFacets(query, speciesFilter, interactorTypeFilter, page, pageSize);
    }

    @RequestMapping(value = "/interactor/findInteractorWithFacet",
            params = {
                    "query",
                    "page",
                    "pageSize"
            },
            method = RequestMethod.GET,
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

        SearchInteractorResult interactorResult = this.interactorSearchService.findInteractorWithFacet(query, speciesFilter, interactorTypeFilter,
                detectionMethodFilter, interactionTypeFilter, interactionHostOrganismFilter,
                isNegativeFilter, minMiScore, maxMiScore, page, pageSize);

        for (SearchInteractor searchInteractor : interactorResult.getContent()) {

            // TODO: Please change the URI FROM THE URL for the interaction web service in production
            String URL = "http://localhost:8082/intact/ws/interaction/countInteractionResult?query={query}&interactorAc={interactorAc}&detectionMethodFilter={detectionMethodFilter}" +
                    "&interactionTypeFilter={interactionTypeFilter}&hostOrganismFilter={hostOrganismFilter}&isNegativeFilter={isNegativeFilter}" +
                    "&minMiscore={minMiscore}&maxMiscore={maxMiscore}";

            String term = searchInteractor.getInteractorId();


            RestTemplate restTemplate = new RestTemplate();
            Long interactionCount = restTemplate.getForObject(URL, Long.class, query, term,
                    detectionMethodFilter != null ? String.join(",", detectionMethodFilter): "",
                    interactionTypeFilter != null ? String.join(",", interactionTypeFilter): "",
                    interactionHostOrganismFilter != null ? String.join(",", interactionHostOrganismFilter) : "",
                    isNegativeFilter, minMiScore, maxMiScore);

            searchInteractor.setInteractionSearchCount(interactionCount);
        }

        return interactorResult;
    }

    @GetMapping(value = "/interactor/findInteractor/{query}",
            produces = {APPLICATION_JSON_VALUE})
    public Page<SearchInteractor> findInteractor(@PathVariable String query) {
        return this.interactorSearchService.findInteractor(query);
    }

    @GetMapping(value = "/interactor/countTotal",
            produces = {APPLICATION_JSON_VALUE})
    public long countTotal() {
        return this.interactorSearchService.countTotal();
    }

}
