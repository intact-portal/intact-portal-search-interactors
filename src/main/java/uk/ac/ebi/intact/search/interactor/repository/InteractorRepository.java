package uk.ac.ebi.intact.search.interactor.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;

import java.util.Set;

import static org.springframework.data.solr.core.query.Query.Operator.AND;

/**
 * @author Elisabet Barrera
 */

@Repository
public interface InteractorRepository extends SolrCrudRepository<SearchInteractor, String>, CustomizedInteractorRepository {

//    List<SearchInteractor> findByName(String name);

    @Facet(fields = {"species_name_str", "interactor_type_str"}, limit = 100)
    @Query(value = "*:*") //TODO: taxId and interactor_type
    FacetPage<SearchInteractor> getTaxIdFacets(Pageable pageable);

    @Facet(fields = {"species_name_str", "interactor_type_str"}, limit = 100)
    @Query(value = "default:*", filters = {"species_name:?1", "interactor_type:?2"}, defaultOperator = AND)
    FacetPage<SearchInteractor> getSpeciesAndInteractorTypeFacets(String query, Set<String> speciesFilter, Set<String> interactorTypeFilter, Pageable pageable);



}
