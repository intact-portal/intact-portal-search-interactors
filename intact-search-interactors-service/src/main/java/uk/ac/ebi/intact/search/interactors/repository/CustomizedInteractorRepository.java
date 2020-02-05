package uk.ac.ebi.intact.search.interactors.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

import java.util.Set;

/**
 * @author Elisabet Barrera
 */

@Repository
public interface CustomizedInteractorRepository {

    /**
     * @param query                             input used to retrieve the interaction
     * @param interactorSpeciesFilter           (Optional) filter interactors by interactor species
     * @param interactorTypeFilter              (Optional) filter interactors by interactor type
     * @param interactionDetectionMethodFilter  (Optional) filter interactors by interaction detection method
     * @param interactionTypeFilter             (Optional) filter interactors by interaction type
     * @param interactionHostOrganismFilter     (Optional) filter interactors by interaction host organism
     * @param isNegativeFilter                  (Optional) filter interactors by negative interaction if true
     * @param minMiScore                        minimum value of mi-score for the interaction related to the interactor
     * @param maxMiScore                        maximum value of mi-score for the interaction related to the interactor
     * @param sort                    field to define the sort of the results
     * @param pageable                page number and size of the request
     * @return the interaction data matching all the criteria
     */
    FacetPage<SearchInteractor> findInteractorWithFacet(
            String query,
            Set<String> interactorSpeciesFilter,
            Set<String> interactorTypeFilter,
            Set<String> interactionDetectionMethodFilter,
            Set<String> interactionTypeFilter,
            Set<String> interactionHostOrganismFilter,
            boolean isNegativeFilter,
            double minMiScore,
            double maxMiScore,
            Sort sort,
            Pageable pageable);

    /**
     * @param query                             input used to retrieve the interaction
     * @param interactorSpeciesFilter           (Optional) filter interactors by interactor species
     * @param interactorTypeFilter              (Optional) filter interactors by interactor type
     * @param interactionDetectionMethodFilter  (Optional) filter interactors by interaction detection method
     * @param interactionTypeFilter             (Optional) filter interactors by interaction type
     * @param interactionHostOrganismFilter     (Optional) filter interactors by interaction host organism
     * @param isNegativeFilter                  (Optional) filter interactors by negative interaction if true
     * @param minMiScore                        minimum value of mi-score for the interaction related to the interactor
     * @param maxMiScore                        maximum value of mi-score for the interaction related to the interactor
     * @param sort                              field to define the sort of the results
     * @param pageable                          page number and size of the request
     * @return the interaction data matching all the criteria
     */
    Page<SearchInteractor> findInteractorForGraphJson(
            String query,
            Set<String> interactorSpeciesFilter,
            Set<String> interactorTypeFilter,
            Set<String> interactionDetectionMethodFilter,
            Set<String> interactionTypeFilter,
            Set<String> interactionHostOrganismFilter,
            boolean isNegativeFilter,
            double minMiScore,
            double maxMiScore,
            Sort sort,
            Pageable pageable);
}
