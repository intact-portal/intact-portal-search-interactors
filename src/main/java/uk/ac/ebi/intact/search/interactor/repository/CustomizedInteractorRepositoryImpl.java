package uk.ac.ebi.intact.search.interactor.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.*;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactor.controller.SearchInteractorResult;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractorFields;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Elisabet Barrera
 */

@Repository
public class CustomizedInteractorRepositoryImpl implements CustomizedInteractorRepository {

    private SolrOperations solrOperations;

    // default minimum counts for faceting
    private static final int FACET_MIN_COUNT = 10000;

    // default sorting for the query results
    //TODO Solve problems with multivalue fields that are not allow to be sorted. Schema-less create all the fields as multivalues
    private static final Sort DEFAULT_QUERY_SORT_WITH_QUERY = new Sort(Sort.Direction.DESC, SearchInteractorFields.INTERACTION_COUNT);

    @Autowired
    public CustomizedInteractorRepositoryImpl(SolrOperations solrOperations) {
        this.solrOperations = solrOperations;
    }

    @Override
    public SearchInteractorResult findInteractorWithFacet(String query, Set<String> speciesFilter, Set<String> interactorTypeFilter, Sort sort, Pageable pageable) {

        // search query
        SimpleFacetQuery search = new SimpleFacetQuery();

        // search criterias
        Criteria conditions = createSearchConditions(query);
        search.addCriteria(conditions);

        // filters
        List<FilterQuery> filterQueries = createFilterQuery(speciesFilter, interactorTypeFilter);
        if (filterQueries != null && !filterQueries.isEmpty()) {
            for (FilterQuery filterQuery : filterQueries) {
                search.addFilterQuery(filterQuery);
            }

        }

        // facet
        FacetOptions facetOptions = new FacetOptions(SearchInteractorFields.SPECIES_NAME_STR, SearchInteractorFields.INTERACTOR_TYPE_STR);
        facetOptions.setFacetLimit(FACET_MIN_COUNT);
        search.setFacetOptions(facetOptions);

        // pagination
        search.setPageRequest(pageable);

        // sorting
        if (sort != null) {
            search.addSort(sort);
        } else {
            search.addSort(DEFAULT_QUERY_SORT_WITH_QUERY);
        }

        return  new SearchInteractorResult(solrOperations.queryForFacetPage(SearchInteractor.INTERACTORS, search, SearchInteractor.class));
    }

    private Criteria createSearchConditions(String searchTerms) {
        Criteria conditions = null;

        //Query
        //TODO Review query formation
        if (searchTerms != null && !searchTerms.isEmpty()) {
            String[] words = searchTerms.split(" ");

            for (String word : words) {
                if (conditions == null) {
                    conditions = new Criteria(SearchInteractorFields.DEFAULT).contains(word)
                    .or(SearchInteractorFields.DEFAULT).is(word);
                } else {
                    conditions = conditions.or(SearchInteractorFields.DEFAULT).contains(word)
                    .or(SearchInteractorFields.DEFAULT).is(word);
                }
            }
        } else {
            //Default Criteria
            conditions = new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD);
        }

        return conditions;
    }

    private List<FilterQuery> createFilterQuery(Set<String> speciesFilter,
                                                Set<String> interactorTypeFilter) {
        List<FilterQuery> filterQueries = new ArrayList<FilterQuery>();

        //Interactor type filter
        createFilterCriteria(speciesFilter, SearchInteractorFields.SPECIES_NAME, filterQueries);

        //Species filter
        createFilterCriteria(interactorTypeFilter, SearchInteractorFields.INTERACTOR_TYPE, filterQueries);

        return filterQueries;
    }

    private void createFilterCriteria(Set<String> values, String field, List<FilterQuery> filterQueries) {

        if (values != null) {
            Criteria conditions = null;

            for (String value : values) {
                if (conditions == null) {
                    conditions = new Criteria(field).is(value);
                } else {
                    conditions = conditions.and(new Criteria(field).is(value));
                }
            }

            if (conditions != null) {
                filterQueries.add(new SimpleFilterQuery(conditions));
            }
        }
    }
}
