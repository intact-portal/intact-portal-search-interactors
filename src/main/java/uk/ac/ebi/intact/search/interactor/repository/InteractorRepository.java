package uk.ac.ebi.intact.search.interactor.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactor.model.Interactor;

import java.util.List;

/**
 * @author Elisabet Barrera
 */

@Repository
public interface InteractorRepository extends SolrCrudRepository<Interactor, String> {

//    List<Interactor> findByName(String name);

}
