package uk.ac.ebi.intact.search.interactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.search.interactor.model.Interactor;
import uk.ac.ebi.intact.search.interactor.repository.InteractorRepository;

import java.util.Collection;

/**
 *
 * Custom and generic CRUD operations for indexing purposes.
 *
 * @author Elisabet Barrera
 */

@Service
public class InteractorIndexService {

    @Autowired
    @Qualifier("interactorRepository")
    private InteractorRepository interactorRepository;


    @Transactional
    public void deleteAll() {
        this.interactorRepository.deleteAll();
    }

    @Transactional
    public void save(Interactor interactor) {
        this.interactorRepository.save(interactor);
    }

    @Transactional
    public void save(Collection<Interactor> interactors) {
        this.interactorRepository.save(interactors);
    }

    @Transactional
    public void delete(String id) {
        this.interactorRepository.delete(id);
    }

}
