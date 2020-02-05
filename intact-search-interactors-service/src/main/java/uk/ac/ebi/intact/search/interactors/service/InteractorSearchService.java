package uk.ac.ebi.intact.search.interactors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactors.repository.InteractorRepository;

import java.util.Optional;
import java.util.Set;

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

    public Iterable<SearchInteractor> findAll() {
        return interactorRepository.findAll();
    }

    public FacetPage<SearchInteractor> getTaxIdFacets(Pageable pageable) {
        return this.interactorRepository.getTaxIdFacets(pageable);
    }

    public FacetPage<SearchInteractor> getTaxIdFacets(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return interactorRepository.getTaxIdFacets(pageRequest);
    }

    public FacetPage<SearchInteractor> getSpeciesAndInteractorTypeFacets(String query, Set<String> interactorSpeciesFilter,
                                                                         Set<String> interactorTypeFilter, int page,
                                                                         int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return interactorRepository.getInteractorSpeciesAndInteractorTypeFacets(query, interactorSpeciesFilter, interactorTypeFilter, pageRequest);
    }

    public FacetPage<SearchInteractor> findInteractorWithFacet(String query,
                                                               Set<String> interactorSpeciesFilter,
                                                               Set<String> interactorTypeFilter,
                                                               Set<String> interactionDetectionMethodFilter,
                                                               Set<String> interactionTypeFilter,
                                                               Set<String> interactionHostOrganismFilter,
                                                               boolean isNegativeFilter,
                                                               double minMiScore,
                                                               double maxMiScore,
                                                               int page,
                                                               int pageSize) {
        return interactorRepository.findInteractorWithFacet(query, interactorSpeciesFilter, interactorTypeFilter, interactionDetectionMethodFilter,
                interactionTypeFilter, interactionHostOrganismFilter, isNegativeFilter, minMiScore, maxMiScore, null,
                PageRequest.of(page, pageSize));
    }

    public Page<SearchInteractor> findInteractorForGraphJson(String query, Set<String> interactorSpeciesFilter, Set<String> interactorTypeFilter,
                                                           Set<String> interactionDetectionMethodFilter, Set<String> interactionTypeFilter,
                                                           Set<String> interactionHostOrganismFilter, boolean isNegativeFilter,
                                                           double minMiScore, double maxMiScore, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return interactorRepository.findInteractorForGraphJson(query, interactorSpeciesFilter, interactorTypeFilter, interactionDetectionMethodFilter,
                interactionTypeFilter, interactionHostOrganismFilter, isNegativeFilter, minMiScore, maxMiScore, null, pageRequest);
    }

    public Page<SearchInteractor> findInteractor(String query) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return interactorRepository.findInteractor(query, pageRequest);
    }

    public Optional<SearchInteractor> findById(String id) {
        return interactorRepository.findById(id);
    }

    public Optional<SearchInteractor> findByInteractorAc(String interactorAc){
        return interactorRepository.findByInteractorAc(interactorAc);
    }

    public long countTotal() {
        return this.interactorRepository.count();
    }

}