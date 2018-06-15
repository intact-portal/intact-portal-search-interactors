package uk.ac.ebi.intact.search.interactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;
import uk.ac.ebi.intact.search.interactor.controller.SearchInteractorResult;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactor.repository.InteractorRepository;

import java.util.Optional;
import java.util.Set;

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
        return interactorRepository.findAll();
    }

    public FacetPage<SearchInteractor> getTaxIdFacets(Pageable pageable) {
        return this.interactorRepository.getTaxIdFacets(pageable);
    }

    public FacetPage<SearchInteractor> getTaxIdFacets(int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return interactorRepository.getTaxIdFacets(pageRequest);
    }

    public FacetPage<SearchInteractor> getSpeciesAndInteractorTypeFacets(String query, Set<String> speciesFilter, Set<String> interactorTypeFilter, int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return interactorRepository.getSpeciesAndInteractorTypeFacets(query, speciesFilter, interactorTypeFilter, pageRequest);
    }

    public SearchInteractorResult findInteractorWithFacet(String query, Set<String> speciesFilter, Set<String> interactorTypeFilter, int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return interactorRepository.findInteractorWithFacet(query, speciesFilter, interactorTypeFilter, null, pageRequest);
    }

    public Page<SearchInteractor> findInteractor(String query) {
        PageRequest pageRequest = new PageRequest(0, 10);
        return interactorRepository.findInteractor(query, pageRequest);
    }

    public Optional<SearchInteractor> findById(String id) {
        return interactorRepository.findById(id);
    }

//    public List<SearchInteractor> retrieveInteractors (String name) {
//        return this.interactorRepository.findByName(name);
//    }

}
