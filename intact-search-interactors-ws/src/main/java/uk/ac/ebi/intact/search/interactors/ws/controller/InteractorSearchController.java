package uk.ac.ebi.intact.search.interactors.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactors.service.InteractorSearchService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Elisabet Barrera
 */

@RestController
public class InteractorSearchController {

    public static final String UPLOADED_BATCH_FILE_PREFIX = "file_";
    //TODO temporary
    @Value("${server.upload.batch.file.path}")
    private String uploadBatchFilePath;
    private InteractorSearchService interactorSearchService;

    @Autowired
    public InteractorSearchController(InteractorSearchService interactorSearchService) {
        this.interactorSearchService = interactorSearchService;
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
    public Map<String, Page<SearchInteractor>> resolveInteractorList(@RequestParam(value = "query") String query, @RequestParam(value = "identifierSearch", required = false) boolean identifierSearch) {

        String searchTerms = extractSearchTerms(query);
        List<String> words = new ArrayList<>();

        if (!searchTerms.isEmpty()) {
            words = Arrays.asList(searchTerms.split("[\\s,\\n]"));
        }

        return this.interactorSearchService.resolveInteractorList(words, identifierSearch);
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
            File uploadedBatchFile = new File(uploadBatchFilePath + File.separator + query);
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
