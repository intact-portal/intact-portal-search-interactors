package uk.ac.ebi.intact.search.interactors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractorFields;
import uk.ac.ebi.intact.search.interactors.repository.InteractorRepository;
import java.util.*;


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

    private static Map<String, Page<SearchInteractor>> sortByTotalElements(Map<String, Page<SearchInteractor>> unsortMap, final boolean order) {

        List<Map.Entry<String, Page<SearchInteractor>>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on total elements
        list.sort((o1, o2) -> {
            if (order) {
                return Long.compare(o1.getValue().getTotalElements(), o2.getValue().getTotalElements());
            } else {
                return Long.compare(o2.getValue().getTotalElements(), o1.getValue().getTotalElements());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Page<SearchInteractor>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Page<SearchInteractor>> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public Map<String, Page<SearchInteractor>> resolveInteractorList(List<String> terms, boolean fuzzySearch, int page, int pageSize) {
        // TODO add the comparator used in sortByTotalElements in the TreeMap so we insert the elements sorted directly.
        Map<String, Page<SearchInteractor>> results = new TreeMap<>();

        // If we have duplicated query terms we keep the result for the last one
        // From HashMap documentation: if the map previously contained a mapping for
        // the key, the old value is replaced by the specified value
        for (String term : terms) {
            results.put(term, resolveInteractor(term, fuzzySearch, page, pageSize));
        }

        return sortByTotalElements(results, false); //Descending
    }

    public Page<SearchInteractor> resolveInteractor(String query, boolean fuzzySearch, int page, int pageSize) {
        return interactorRepository.resolveInteractor(query, fuzzySearch, Sort.by(Sort.Direction.DESC, SearchInteractorFields.INTERACTION_COUNT), PageRequest.of(page, pageSize));
    }

    public Page<SearchInteractor> findInteractorSuggestions(String query, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return interactorRepository.findInteractorSuggestions(query, pageRequest);
    }

    public Optional<SearchInteractor> findById(String id) {
        return interactorRepository.findById(id);
    }

    public long countTotal() {
        return this.interactorRepository.count();
    }

}