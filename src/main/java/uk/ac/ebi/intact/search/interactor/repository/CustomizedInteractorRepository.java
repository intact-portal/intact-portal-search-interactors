package uk.ac.ebi.intact.search.interactor.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactor.controller.SearchInteractorResult;

import java.util.Set;

/**
 * @author Elisabet Barrera
 */

@Repository
public interface CustomizedInteractorRepository {

    /**
     * Count the facets per modification name and species names
     * @param query (term to search by in text field and highest ratio peptide sequences) mandatory
     * @param interactorTypeFilter (interactor type to filter by) optional
     * @param speciesFilter (species names to filter by) optional
     * @return a facet page with the names and the number of hits per name
     */

    SearchInteractorResult findInteractorWithFacet(
            String query,
            Set<String> speciesFilter,
            Set<String> interactorTypeFilter,
            Set<String> detectionMethodFilter,
            Set<String> interactionTypeFilter,
            Set<String> interactionHostOrganismFilter,
            boolean isNegativeFilter,
            double minMiScore,
            double maxMiScore,
            Sort sort,
            Pageable pageable);


}
