package uk.ac.ebi.intact.search.interactor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;

import java.util.Set;

import static org.springframework.data.solr.core.query.Query.Operator.AND;
import static uk.ac.ebi.intact.search.interactor.model.SearchInteractorFields.*;

/**
 * @author Elisabet Barrera
 */

@Repository
public interface InteractorRepository extends SolrCrudRepository<SearchInteractor, String>, CustomizedInteractorRepository {

//    List<SearchInteractor> findByName(String name);

    @Facet(fields = {SPECIES_NAME_STR, INTERACTOR_TYPE_STR}, limit = 100)
    @Query(value = "*:*") //TODO: taxId and interactor_type
    FacetPage<SearchInteractor> getTaxIdFacets(Pageable pageable);

    @Facet(fields = {SPECIES_NAME_STR, INTERACTOR_TYPE_STR}, limit = 100)
    @Query(value = DEFAULT + ":?0", filters = {SPECIES_NAME + ":?1", INTERACTOR_TYPE + "?2"}, defaultOperator = AND)
    FacetPage<SearchInteractor> getSpeciesAndInteractorTypeFacets(String query, Set<String> speciesFilter, Set<String> interactorTypeFilter, Pageable pageable);

    //TODO Add this field as default. It has text_en as FieldType in solr and copy all the values for now
    @Query(value = "default:?0")
//    @Query(value = DEFAULT + ":?0")
    Page<SearchInteractor> findInteractor(String query, Pageable pageable);


}
