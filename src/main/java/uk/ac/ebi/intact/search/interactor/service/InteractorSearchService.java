package uk.ac.ebi.intact.search.interactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactor.repository.InteractorRepository;

import java.util.Optional;

/**
 *
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

    public Iterable<SearchInteractor> findAll() {
        return this.interactorRepository.findAll();
    }

    public FacetPage<SearchInteractor> getTaxIdFacets(Pageable pageable) {return this.interactorRepository.getTaxIdFacets(pageable);}

    public Optional<SearchInteractor> findById(String id) {
        return this.interactorRepository.findById(id);
    }

//    public List<SearchInteractor> retrieveInteractors (String name) {
//        return this.interactorRepository.findByName(name);
//    }

}
