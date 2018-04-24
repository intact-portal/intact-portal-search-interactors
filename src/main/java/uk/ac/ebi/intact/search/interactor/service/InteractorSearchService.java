package uk.ac.ebi.intact.search.interactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.ac.ebi.intact.search.interactor.model.Interactor;
import uk.ac.ebi.intact.search.interactor.repository.InteractorRepository;

import java.util.List;

/**
 *
 * Custom and generic CRUD operations for searching purposes.
 *
 * @author Elisabet Barrera
 */

@Service
public class InteractorSearchService {

    @Autowired
    @Qualifier("interactorRepository")
    private InteractorRepository interactorRepository;

    public Iterable<Interactor> findAll() {
        return this.interactorRepository.findAll();
    }

    public Interactor findBy(String id) {
        return this.interactorRepository.findOne(id);
    }

//    public List<Interactor> retrieveInteractors (String name) {
//        return this.interactorRepository.findByName(name);
//    }

}
