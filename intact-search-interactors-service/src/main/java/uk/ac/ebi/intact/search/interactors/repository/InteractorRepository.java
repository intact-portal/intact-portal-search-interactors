package uk.ac.ebi.intact.search.interactors.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

import static uk.ac.ebi.intact.search.interactors.model.SearchInteractorFields.*;

/**
 * @author Elisabet Barrera
 */

@Repository
public interface InteractorRepository extends SolrCrudRepository<SearchInteractor, String>, CustomizedInteractorRepository {

    @Query(value = DEFAULT + ":?0")
    Page<SearchInteractor> findInteractor(String query, Pageable pageable);

/*    @Query(value = INTERACTOR_NAME + ":?0 OR "
            + INTERACTOR_DESCRIPTION + ":?0 OR "
            + INTERACTOR_ALIAS + ":?0 OR "
            + INTERACTOR_IDENTIFIERS + ":?0",
            fields = {INTERACTOR_AC,
                    INTERACTOR_NAME,
                    INTERACTOR_DESCRIPTION,
                    INTERACTOR_PREFERRED_ID,
                    INTERACTOR_SPECIES_NAME,
                    INTERACTOR_TAX_ID,
                    INTERACTOR_TYPE,
                    INTERACTION_COUNT}
    )
    @Highlight(fields = {INTERACTOR_NAME,
            INTERACTOR_DESCRIPTION,
            INTERACTOR_ALIAS,
            INTERACTOR_IDENTIFIERS}, prefix = "<b>", postfix = "</b>")
    Page<SearchInteractor> resolveInteractor(String query, Pageable pageable);*/

    @Query(value = INTERACTOR_NAME + ":?0 OR "
            + INTERACTOR_ALIAS + ":?0 OR "
            + INTERACTOR_IDENTIFIERS + ":?0",
            fields = {INTERACTOR_AC,
                    INTERACTOR_NAME,
                    INTERACTOR_DESCRIPTION,
                    INTERACTOR_PREFERRED_ID,
                    INTERACTOR_SPECIES_NAME,
                    INTERACTOR_TAX_ID,
                    INTERACTOR_TYPE,
                    INTERACTION_COUNT}
    )
    @Highlight(fields = {INTERACTOR_NAME,
            INTERACTOR_ALIAS,
            INTERACTOR_IDENTIFIERS}, prefix = "<b>", postfix = "</b>")
    Page<SearchInteractor> resolveInteractorByIdsOrName(String query, Pageable pageable);

}
