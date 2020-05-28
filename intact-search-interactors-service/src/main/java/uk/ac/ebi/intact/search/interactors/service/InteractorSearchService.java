package uk.ac.ebi.intact.search.interactors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactors.repository.InteractorRepository;

import java.util.*;

import static uk.ac.ebi.intact.search.interactors.utils.SearchInteractorUtils.escapeQueryChars;

/**
 * Custom and generic CRUD operations for searching purposes.
 *
 * @author Elisabet Barrera
 */

@Service
public class InteractorSearchService {

    private final InteractorRepository interactorRepository;

    @Autowired
    public InteractorSearchService(@Qualifier("interactorRepository") InteractorRepository interactorRepository) {
        this.interactorRepository = interactorRepository;
    }

    public Map<String, Page<SearchInteractor>> resolveInteractorList(List<String> terms) {

        Map<String, Page<SearchInteractor>> results = new TreeMap<>();

        // If we have duplicated query terms we keep the result for the last one
        // From HashMap documentation: if the map previously contained a mapping for
        // the key, the old value is replaced by the specified value
        for (String term : terms) {
            results.put(term, resolveInteractor(term));
        }

        return sortByTotalElements(results, false); //Descending
    }

    public Page<SearchInteractor> resolveInteractor(String query) {
        //TODO Paginate the results (for know it should cover disambiguation with 50 elements per page)
        return interactorRepository.resolveInteractor(escapeQueryChars(query), PageRequest.of(0,50));
    }

    public Page<SearchInteractor> findInteractor(String query) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return interactorRepository.findInteractor(query, pageRequest);
    }

    public Optional<SearchInteractor> findById(String id) {
        return interactorRepository.findById(id);
    }

    public long countTotal() {
        return this.interactorRepository.count();
    }

    private static Map<String, Page<SearchInteractor>> sortByTotalElements(Map<String, Page<SearchInteractor>> unsortMap, final boolean order) {

        List<Map.Entry<String, Page<SearchInteractor>>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> {
            if (order) {
                return Long.compare(o1.getValue().getTotalElements(), o2.getValue().getTotalElements());
            } else {
                return Long.compare(o2.getValue().getTotalElements(), o1.getValue().getTotalElements());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Page<SearchInteractor>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Page<SearchInteractor>> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
