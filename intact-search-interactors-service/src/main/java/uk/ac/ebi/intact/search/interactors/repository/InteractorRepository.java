package uk.ac.ebi.intact.search.interactors.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

import static uk.ac.ebi.intact.search.interactors.model.SearchInteractorFields.DEFAULT;

/**
 * @author Elisabet Barrera
 */

@Repository
public interface InteractorRepository extends SolrCrudRepository<SearchInteractor, String>, CustomizedInteractorRepository {

    @Query(value = DEFAULT + ":?0")
    Page<SearchInteractor> findInteractor(String query, Pageable pageable);
}
