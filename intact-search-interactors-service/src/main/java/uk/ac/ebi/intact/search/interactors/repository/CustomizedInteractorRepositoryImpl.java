package uk.ac.ebi.intact.search.interactors.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.SimpleField;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightPage;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

import static uk.ac.ebi.intact.search.interactors.model.SearchInteractor.INTERACTORS;
import static uk.ac.ebi.intact.search.interactors.model.SearchInteractorFields.*;

/**
 * Created by anjali on 13/02/20.
 */
public class CustomizedInteractorRepositoryImpl implements CustomizedInteractorRepository {
    // default minimum counts for faceting
    private static final int FACET_MIN_COUNT = 10000;
    private SolrOperations solrOperations;

    // default sorting for the query results
    //TODO Solve problems with multivalue fields that are not allow to be sorted. Schema-less create all the fields as multivalues
//    private static final Sort DEFAULT_QUERY_SORT_WITH_QUERY = new Sort(Sort.Direction.DESC, SearchInteractorFields.INTERACTION_COUNT);

    @Autowired
    public CustomizedInteractorRepositoryImpl(SolrOperations solrOperations) {
        this.solrOperations = solrOperations;
    }

    @Override
    public HighlightPage<SearchInteractor> resolveInteractor(String query, boolean fuzzySearch, Sort sort, Pageable pageable) {


        // search query
        SimpleHighlightQuery search = new SimpleHighlightQuery();

        // search criterias
        if (fuzzySearch) {
            Criteria fuzzyInteractorSearchCriteria = createFuzzySearchConditions(query);
            search.addCriteria(fuzzyInteractorSearchCriteria);
        } else {
            Criteria exactInteractorSearchCriteria = createExactSearchConditions(query);
            search.addCriteria(exactInteractorSearchCriteria);
        }

        // pagination
        search.setPageRequest(pageable);

        // sorting
        if (sort != null) {
            search.addSort(sort);
        }

        //highlighting
        search.setHighlightOptions(new HighlightOptions()
                .setSimplePrefix("<b>")
                .setSimplePostfix("</b>"));

        //projection

        //interactor details
        search.addProjectionOnField(new SimpleField(INTERACTOR_AC));
        search.addProjectionOnField(new SimpleField(INTERACTOR_NAME));
        search.addProjectionOnField(new SimpleField(INTERACTOR_DESCRIPTION));
        search.addProjectionOnField(new SimpleField(INTERACTOR_PREFERRED_ID));
        search.addProjectionOnField(new SimpleField(INTERACTOR_SPECIES_NAME));
        search.addProjectionOnField(new SimpleField(INTERACTOR_TAX_ID));
        search.addProjectionOnField(new SimpleField(INTERACTOR_TYPE));
        search.addProjectionOnField(new SimpleField(INTERACTION_COUNT));

        return solrOperations.queryForHighlightPage(INTERACTORS, search, SearchInteractor.class);
    }

    public Criteria createFuzzySearchConditions(String searchTerm) {
        Criteria userConditions = null;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Criteria identifierCriteria = new Criteria(INTERACTOR_IDENTIFIERS).boost(10).is(searchTerm);
            Criteria nameCriteria = new Criteria(INTERACTOR_NAME).boost(8).is(searchTerm);
            Criteria aliasCriteria = new Criteria(INTERACTOR_ALIAS).boost(2).is(searchTerm);
            Criteria descriptionCriteria = new Criteria(INTERACTOR_DESCRIPTION).is(searchTerm);
            userConditions = identifierCriteria.or(nameCriteria).or(aliasCriteria).or(descriptionCriteria);
        }
        return userConditions;
    }

    public Criteria createExactSearchConditions(String searchTerm) {
        Criteria userConditions = null;
        searchTerm = "\"" + searchTerm + "\"";
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Criteria identifierCriteria = new Criteria(INTERACTOR_IDENTIFIERS).boost(10).expression(searchTerm);
            Criteria nameCriteria = new Criteria(INTERACTOR_NAME).boost(8).expression(searchTerm);
            Criteria aliasCriteria = new Criteria(INTERACTOR_ALIAS).boost(2).expression(searchTerm);
            userConditions = identifierCriteria.or(nameCriteria).or(aliasCriteria);
        }
        return userConditions;
    }
}
