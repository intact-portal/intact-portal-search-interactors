package uk.ac.ebi.intact.search.interactors.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

import java.util.Optional;
import java.util.Set;

import static org.springframework.data.solr.core.query.Query.Operator.AND;
import static uk.ac.ebi.intact.search.interactors.model.SearchInteractorFields.*;

/**
 * @author Elisabet Barrera
 */

@Repository
public interface InteractorRepository extends SolrCrudRepository<SearchInteractor, String> {

//    List<SearchInteractor> findByName(String name);

    @Facet(fields = {INTERACTOR_SPECIES_NAME_STR, INTERACTOR_TYPE_STR}, limit = 100)
    @Query(value = "*:*")
        //TODO: taxId and interactor_type
    FacetPage<SearchInteractor> getTaxIdFacets(Pageable pageable);

    @Facet(fields = {INTERACTOR_SPECIES_NAME_STR, INTERACTOR_TYPE_STR}, limit = 100)
    @Query(value = DEFAULT + ":?0", filters = {INTERACTOR_SPECIES_NAME + ":?1", INTERACTOR_TYPE + ":?2"}, defaultOperator = AND)
    FacetPage<SearchInteractor> getInteractorSpeciesAndInteractorTypeFacets(String query, Set<String> interactorSpeciesFilter, Set<String> interactorTypeFilter, Pageable pageable);

    //TODO Add this field as default. It has text_en_splitting_tight_ngram as FieldType in solr and copy all the values for now
    @Query(value = DEFAULT + ":?0")
    Page<SearchInteractor> findInteractor(String query, Pageable pageable);

    Optional<SearchInteractor> findByInteractorAc(String interactorAc);

    @Query(value = INTERACTOR_NAME + ":?0 OR "
            + INTERACTOR_DESCRIPTION + ":?0 OR "
            + INTERACTOR_ALIAS + ":?0 OR "
            + INTERACTOR_IDENTIFIERS + ":?0" ,
            fields = { INTERACTOR_AC,
                    INTERACTOR_NAME,
                    INTERACTOR_DESCRIPTION,
                    INTERACTOR_PREFERRED_ID,
                    INTERACTOR_SPECIES_NAME,
                    INTERACTOR_TYPE,
                    INTERACTION_COUNT })
    @Highlight(fields = {INTERACTOR_NAME,
            INTERACTOR_DESCRIPTION,
            INTERACTOR_ALIAS,
            INTERACTOR_IDENTIFIERS}, prefix = "<b>", postfix = "</b>")
    Page<SearchInteractor> resolveInteractor(String query, Pageable pageable);

}
