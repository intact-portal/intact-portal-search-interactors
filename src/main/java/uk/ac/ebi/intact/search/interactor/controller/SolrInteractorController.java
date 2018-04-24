package uk.ac.ebi.intact.search.interactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.intact.search.interactor.model.Interactor;
import uk.ac.ebi.intact.search.interactor.service.InteractorIndexService;
import uk.ac.ebi.intact.search.interactor.service.InteractorSearchService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Elisabet Barrera
 */

@RestController
public class SolrInteractorController {

    private InteractorIndexService interactorIndexService;
    private InteractorSearchService interactorSearchService;

    @Autowired
    public SolrInteractorController(InteractorIndexService interactorIndexService,
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
        this.interactorIndexService.delete(interactorId);
    }

    @RequestMapping("/save")
    public void saveAllDocuments(Collection<Interactor> interactors) {
        //Store Documents
        this.interactorIndexService.save(interactors);
    }

    @RequestMapping("/getAll")
    public List<Interactor> getAllDocs() {
        List<Interactor> documents = new ArrayList<>();
        // iterate all documents and add it to list
        for (Interactor doc : this.interactorSearchService.findAll()) {
            documents.add(doc);
        }
        return documents;
    }
}
