package uk.ac.ebi.intact.search.interactors.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactors.service.InteractorSearchService;

import java.io.*;
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
    public Page<SearchInteractor> findInteractor(@PathVariable String query,
                                                 @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return this.interactorSearchService.findInteractorSuggestions(query, page, pageSize);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/list/resolve",
            produces = {APPLICATION_JSON_VALUE})
    public Map<String, Page<SearchInteractor>> resolveInteractorList(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "fuzzySearch", defaultValue = "true", required = false) boolean fuzzySearch,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "50", required = false) int pageSize
    ) {

        String searchTerms = extractSearchTerms(query);
        List<String> words = new ArrayList<>();

        if (!searchTerms.isEmpty()) {
            if (searchTerms.startsWith("\"") && searchTerms.endsWith("\"")) {
                words.add(searchTerms.substring(1, searchTerms.length() - 1));
            } else if (searchTerms.indexOf(",") != -1) {
                words = Arrays.asList(searchTerms.split("[\\,]"));
            } else {
                words = Arrays.asList(searchTerms.split("[\\s,\\n]"));
            }
        }

        return this.interactorSearchService.resolveInteractorList(words, fuzzySearch, page, pageSize);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/countTotal",
            produces = {APPLICATION_JSON_VALUE})
    public long countTotal() {
        return this.interactorSearchService.countTotal();
    }

    @PostMapping(value = "/uploadFile",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadBatchFile(
            @RequestParam(value = "file", required = true) MultipartFile file) {
        String rootPath = uploadBatchFilePath;
        String uploadBatchFileName = null;
        HttpStatus httpStatus = HttpStatus.OK;
        if (file != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                // Creating the directory to store file
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                uploadBatchFileName = UPLOADED_BATCH_FILE_PREFIX + file.hashCode();
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + uploadBatchFileName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            } catch (IOException ioe) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                ioe.printStackTrace();
            }
        } else {
            httpStatus = HttpStatus.EXPECTATION_FAILED;
        }

//        JSONObject result = new JSONObject();
//        result.put("data", uploadBatchFileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", APPLICATION_JSON_VALUE);
        headers.add("X-Clacks-Overhead", "headers");

        return new ResponseEntity<String>(uploadBatchFileName, headers, httpStatus);
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
