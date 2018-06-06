package uk.ac.ebi.intact.search.interactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactor.service.InteractorIndexService;
import uk.ac.ebi.intact.search.interactor.service.InteractorSearchService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    @RequestMapping("/")
    public String SpringBootSolrExample() {
        return "Welcome to Spring Boot solr Example";
    }

    @RequestMapping("/deleteAll")
    public String deleteAllDocuments() {
        try { //delete all documents from solr core
            this.interactorIndexService.deleteAll();
            return "documents deleted succesfully!";
        }catch (Exception e){
            return "Failed to delete documents";
        }
    }

    @RequestMapping("/delete/{interactorId}")
    public void delete(@PathVariable String interactorId) {
        this.interactorIndexService.deleteById(interactorId);
    }

    @RequestMapping("/save")
    public void saveAllDocuments(Collection<SearchInteractor> searchInteractors) {
        //Store Documents
        this.interactorIndexService.saveAll(searchInteractors);
    }

    @RequestMapping("/getAll")
    public List<SearchInteractor> getAllDocs() {
        List<SearchInteractor> documents = new ArrayList<>();
        // iterate all documents and add it to list
        for (SearchInteractor doc : this.interactorSearchService.findAll()) {
            documents.add(doc);
        }
        return documents;
    }

    @RequestMapping("/getAllTaxIdFacets")
    public FacetPage<SearchInteractor> getAllDocsTaxIdFacets() {
        return this.interactorSearchService.getTaxIdFacets(new PageRequest(0,1));
    }

    @RequestMapping(value = "/getAllTaxIdFacets", params = {"page", "size"}, method = RequestMethod.GET)
    public FacetPage<SearchInteractor> getAllDocsTaxIdFacets(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "1") int size) {
        return this.interactorSearchService.getTaxIdFacets(page, size);
    }

    @RequestMapping(value = "/getSpeciesAndInteractorTypeFacets",
            params = {
                "query",
                "speciesFilter",
                "interactorTypeFilter",
                "page",
                "pageSize"
            },
            method = RequestMethod.GET)
    public FacetPage<SearchInteractor> getSpeciesAndInteractorTypeFacets(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "speciesFilter", required = false) Set<String> speciesFilter,
            @RequestParam(value = "interactorTypeFilter", required = false) Set<String> interactorTypeFilter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return this.interactorSearchService.getSpeciesAndInteractorTypeFacets(query, speciesFilter, interactorTypeFilter, page, pageSize);
    }

    @RequestMapping(value = "/findInteractorWithFacet",
            params = {
                    "query",
                    "speciesFilter",
                    "interactorTypeFilter",
                    "page",
                    "pageSize"
            },
            method = RequestMethod.GET)
    public SearchInteractorResult findInteractorWithFacet(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "speciesFilter", required = false) Set<String> speciesFilter,
            @RequestParam(value = "interactorTypeFilter", required = false) Set<String> interactorTypeFilter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return this.interactorSearchService.findInteractorWithFacet(query, speciesFilter, interactorTypeFilter, page, pageSize);
    }

    @RequestMapping("/findInteractor/{query}")
    public Page<SearchInteractor> findInteractor(
            @PathVariable String query) {
        return this.interactorSearchService.findInteractor(query);
    }
}
