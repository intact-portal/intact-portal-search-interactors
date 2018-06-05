package uk.ac.ebi.intact.search.interactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactor.repository.InteractorRepository;

/**
 * Custom and generic CRUD operations for indexing purposes.
 *
 * @author Elisabet Barrera
 */

@Service
public class InteractorIndexService {

    private final InteractorRepository interactorRepository;

    @Autowired
    public InteractorIndexService(@Qualifier("interactorRepository") InteractorRepository interactorRepository) {
        this.interactorRepository = interactorRepository;
    }

    @Transactional
    public void deleteAll() {
        this.interactorRepository.deleteAll();
    }

    @Transactional
    public void save(SearchInteractor searchInteractor) {
        this.interactorRepository.save(searchInteractor);
    }

    @Transactional
    public void saveAll(Iterable<SearchInteractor> searchInteractors) {
        this.interactorRepository.saveAll(searchInteractors);
    }

    @Transactional
    public void deleteById(String id) {
        this.interactorRepository.deleteById(id);
    }

}
