package uk.ac.ebi.intact.search.interactor.repository;

import org.apache.solr.common.params.FacetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.*;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactor.controller.SearchInteractorResult;
import uk.ac.ebi.intact.search.interactor.model.SearchInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static uk.ac.ebi.intact.search.interactor.model.SearchInteractor.*;
import static uk.ac.ebi.intact.search.interactor.model.SearchInteractorFields.*;

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
//    private static final Sort DEFAULT_QUERY_SORT_WITH_QUERY = new Sort(Sort.Direction.DESC, SearchInteractorFields.INTERACTION_COUNT);

    @Autowired
    public CustomizedInteractorRepositoryImpl(SolrOperations solrOperations) {
        this.solrOperations = solrOperations;
    }

    @Override
    public SearchInteractorResult findInteractorWithFacet(String query, Set<String> speciesFilter, Set<String> interactorTypeFilter,
                                                          Set<String> detectionMethodFilter, Set<String> interactionTypeFilter,
                                                          boolean isNegativeFilter, double minMiScore, double maxMiScore,
                                                          Sort sort, Pageable pageable) {

        // search query
        SimpleFacetQuery search = new SimpleFacetQuery();

        // search criterias
        Criteria conditions = createSearchConditions(query);
        search.addCriteria(conditions);

        // filters
        List<FilterQuery> filterQueries = createFilterQuery(speciesFilter, interactorTypeFilter, detectionMethodFilter,
                interactionTypeFilter, isNegativeFilter, minMiScore, maxMiScore);

        if (filterQueries != null && !filterQueries.isEmpty()) {
            for (FilterQuery filterQuery : filterQueries) {
                search.addFilterQuery(filterQuery);
            }

        }

        // facet
        FacetOptions facetOptions = new FacetOptions(
                SPECIES_NAME_STR, INTERACTOR_TYPE_STR,
                INTERACTION_DETECTION_METHOD, INTERACTION_TYPE,
                INTERACTION_NEGATIVE, INTERACTION_MISCORE);
        facetOptions.setFacetLimit(FACET_MIN_COUNT);
        facetOptions.addFacetByRange(
                new FacetOptions.FieldWithNumericRangeParameters(INTERACTION_MISCORE, 0d, 1d, 0.01d)
                        .setHardEnd(true)
                        .setInclude(FacetParams.FacetRangeInclude.ALL)
        );
        /*facetOptions.setFacetSort(FacetOptions.FacetSort.COUNT);*/
        search.setFacetOptions(facetOptions);

        // pagination
        search.setPageRequest(pageable);

        // sorting
        if (sort != null) {
            search.addSort(sort);
        }
//        else {
//            search.addSort(DEFAULT_QUERY_SORT_WITH_QUERY);
//        }

        return new SearchInteractorResult(solrOperations.queryForFacetPage(INTERACTORS, search, SearchInteractor.class));
    }

    private Criteria createSearchConditions(String searchTerms) {
        Criteria conditions = null;

        //Query
        //TODO Review query formation
        if (searchTerms != null && !searchTerms.isEmpty()) {
            String[] words = searchTerms.split(" ");

            for (String word : words) {
                if (conditions == null) {
                    conditions = new Criteria(DEFAULT).contains(word)
                    .or(INTERACTOR_ID_STR).is(word);
                } else {
                    conditions = conditions.or(DEFAULT).contains(word)
                    .or(INTERACTOR_ID_STR).is(word);
                }
            }
        } else {
            //Default Criteria
            conditions = new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD);
        }

        return conditions;
    }

    private List<FilterQuery> createFilterQuery(Set<String> speciesFilter,
                                                Set<String> interactorTypeFilter,
                                                Set<String> detectionMethodFilter,
                                                Set<String> interactionTypeFilter,
                                                boolean isNegativeFilter,
                                                double minMiScore,
                                                double maxMiScore) {
        List<FilterQuery> filterQueries = new ArrayList<>();

        //Interactor type filter
        createFilterCriteria(speciesFilter, SPECIES_NAME, filterQueries);

        //Species filter
        createFilterCriteria(interactorTypeFilter, INTERACTOR_TYPE, filterQueries);

        //Interaction Detection Method filter
        createFilterCriteria(detectionMethodFilter, INTERACTION_DETECTION_METHOD, filterQueries);

        //Interaction Type filter
        createFilterCriteria(interactionTypeFilter, INTERACTION_TYPE, filterQueries);

        //Interaction Negative filter
        createNegativeFilterCriteria(isNegativeFilter, filterQueries);

        //Interaction MiScore filter
        createMiScoreFilterCriteria(minMiScore, maxMiScore, filterQueries);

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

    private void createNegativeFilterCriteria(boolean value, List<FilterQuery> filterQueries) {

        Criteria conditions = new Criteria(INTERACTION_NEGATIVE).is(value);

        filterQueries.add(new SimpleFilterQuery(conditions));
    }

    private void createMiScoreFilterCriteria(double minMiScore, double maxMiScore, List<FilterQuery> filterQueries) {

        Criteria conditions = new Criteria(INTERACTION_MISCORE).between(minMiScore, maxMiScore);

        filterQueries.add(new SimpleFilterQuery(conditions));
    }
}
