package uk.ac.ebi.intact.search.interactors.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.intact.search.interactions.service.InteractionSearchService;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactors.service.InteractorSearchService;
import uk.ac.ebi.intact.search.interactors.ws.controller.model.InteractorSearchResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Elisabet Barrera
 */

@RestController
public class InteractorSearchController {

    //TODO temporary
    @Value("${server.upload.batch.file.path}")
    private String uploadBatchFilePath;

    public static final String UPLOADED_BATCH_FILE_PREFIX = "file_";


    private InteractorSearchService interactorSearchService;
    private InteractionSearchService interactionSearchService;

    @Autowired
    public InteractorSearchController(InteractorSearchService interactorSearchService,
                                      InteractionSearchService interactionSearchService) {
        this.interactionSearchService = interactionSearchService;
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
            produces = {APPLICATION_JSON_VALUE})
    public FacetPage<SearchInteractor> getAllDocsTaxIdFacets(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return this.interactorSearchService.getTaxIdFacets(page, size);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getSpeciesAndInteractorTypeFacets",
            params = {
                    "query",
                    "interactorSpeciesFilter",
                    "interactorTypeFilter",
                    "page",
                    "pageSize"
            },
            produces = {APPLICATION_JSON_VALUE})
    public FacetPage<SearchInteractor> getSpeciesAndInteractorTypeFacets(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "interactorSpeciesFilter", required = false) Set<String> interactorSpeciesFilter,
            @RequestParam(value = "interactorTypeFilter", required = false) Set<String> interactorTypeFilter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return this.interactorSearchService.getSpeciesAndInteractorTypeFacets(query, interactorSpeciesFilter, interactorTypeFilter, page, pageSize);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/findInteractorWithFacet",
            params = {
                    "query",
                    "page",
                    "pageSize"
            },
            produces = {APPLICATION_JSON_VALUE})
    public InteractorSearchResult findInteractorWithFacet(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "interactorSpeciesFilter", required = false) Set<String> interactorSpeciesFilter,
            @RequestParam(value = "interactorTypeFilter", required = false) Set<String> interactorTypeFilter,
            @RequestParam(value = "interactionDetectionMethodFilter", required = false) Set<String> interactionDetectionMethodFilter,
            @RequestParam(value = "interactionTypeFilter", required = false) Set<String> interactionTypeFilter,
            @RequestParam(value = "interactionHostOrganismFilter", required = false) Set<String> interactionHostOrganismFilter,
            @RequestParam(value = "isNegativeFilter", required = false) boolean isNegativeFilter,
            @RequestParam(value = "minMiScore", defaultValue = "0", required = false) double minMiScore,
            @RequestParam(value = "maxMiScore", defaultValue = "1", required = false) double maxMiScore,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        FacetPage<SearchInteractor> interactorResult = interactorSearchService.findInteractorWithFacet(
                query,
                interactorSpeciesFilter,
                interactorTypeFilter,
                interactionDetectionMethodFilter,
                interactionTypeFilter,
                interactionHostOrganismFilter,
                isNegativeFilter,
                minMiScore,
                maxMiScore,
                page,
                pageSize);

        for (SearchInteractor searchInteractor : interactorResult.getContent()) {

            /* TODO: Pass the interSpecies from the top, it can change the result of the query */
            Long interactionCount = interactionSearchService.countInteractionResult(
                    query,
                    searchInteractor.getInteractorAc(),
                    interactorSpeciesFilter,
                    interactorTypeFilter,
                    interactionDetectionMethodFilter,
                    interactionTypeFilter,
                    interactionHostOrganismFilter,
                    isNegativeFilter,
                    minMiScore,
                    maxMiScore,
                    false);

            searchInteractor.setInteractionSearchCount(interactionCount);
        }

        return new InteractorSearchResult(interactorResult);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/findInteractor/{query}",
            produces = {APPLICATION_JSON_VALUE})
    public Page<SearchInteractor> findInteractor(@PathVariable String query) {
        return this.interactorSearchService.findInteractor(query);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/list/resolve",
            produces = {APPLICATION_JSON_VALUE})
    public Map<String, Page<SearchInteractor>> resolveInteractorList(@RequestParam(value = "query") String query) {

        String searchTerms = extractSearchTerms(query);
        List<String> words = new ArrayList<>();

        if (!searchTerms.isEmpty()) {
            words = Arrays.asList(searchTerms.split("[\\s,\\n]"));
        }

        return this.interactorSearchService.resolveInteractorList(words);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/countTotal",
            produces = {APPLICATION_JSON_VALUE})
    public long countTotal() {
        return this.interactorSearchService.countTotal();
    }

    //TODO temporary
    private String extractSearchTerms(String query) {

        StringBuilder searchTerms = new StringBuilder();

        if (query.startsWith(UPLOADED_BATCH_FILE_PREFIX)) {
            File uploadedBatchFile = new File(uploadBatchFilePath + query);
            if (uploadedBatchFile.exists()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(uploadedBatchFile));
                    String line;
                    int count = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (count > 0) {
                            searchTerms.append(",").append(line);
                        } else {
                            searchTerms = new StringBuilder(line);
                        }
                        count++;
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            searchTerms = new StringBuilder(query);
        }

        return searchTerms.toString();
    }
}
