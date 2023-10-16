package uk.ac.ebi.intact.search.interactors.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;
import uk.ac.ebi.intact.search.interactors.utils.SearchInteractorUtils;

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
    public FacetAndHighlightPage<SearchInteractor> resolveInteractor(String query, boolean fuzzySearch, Sort sort, Pageable pageable) {


        // search query
        SimpleFacetAndHighlightQuery search = new SimpleFacetAndHighlightQuery();

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
            search.addSort(Sort.by(Sort.Direction.DESC, "score"));
            search.addSort(sort);
        }

        //highlighting
        search.setHighlightOptions(new HighlightOptions()
                .addField(INTERACTOR_IDENTIFIERS)
                .addField(INTERACTOR_NAME)
                .addField(INTERACTOR_ALIAS)
                .addField(INTERACTOR_DESCRIPTION)
                .setSimplePrefix("<b>")
                .setSimplePostfix("</b>"));

        //projection

        // TODO Check and remove facets, are not used anymore
        // facet
        // Adds exclude tags in solr to allow calculate properly the facets for multiselection in species and interactor type
        FacetOptions facetOptions = new FacetOptions(
                INTERACTOR_SPECIES_NAME,
                INTERACTOR_SPECIES_NAME_STR,
                INTERACTOR_TYPE,
                INTERACTOR_TYPE_STR
        );
//        facetOptions.setFacetLimit(FACET_MIN_COUNT);

//        facetOptions.getFieldsWithParameters().add(new FacetOptions.FieldWithFacetParameters(INTERACTOR_SPECIES_NAME_STR).setMethod("enum"));
//        facetOptions.getFieldsWithParameters().add(new FacetOptions.FieldWithFacetParameters(INTERACTOR_TYPE_STR).setMethod("enum"));

//        facetOptions.setFacetSort(FacetOptions.FacetSort.COUNT);
        search.setFacetOptions(facetOptions);

        //interactor details
        search.addProjectionOnField(new SimpleField(INTERACTOR_AC));
        search.addProjectionOnField(new SimpleField(INTERACTOR_NAME));
        search.addProjectionOnField(new SimpleField(INTERACTOR_DESCRIPTION));
        search.addProjectionOnField(new SimpleField(INTERACTOR_PREFERRED_ID));
        search.addProjectionOnField(new SimpleField(INTERACTOR_SPECIES_NAME));
        search.addProjectionOnField(new SimpleField(INTERACTOR_TAX_ID));
        search.addProjectionOnField(new SimpleField(INTERACTOR_TAX_ID_STYLED));
        search.addProjectionOnField(new SimpleField(INTERACTOR_TYPE));
        search.addProjectionOnField(new SimpleField(INTERACTOR_TYPE_MI_IDENTIFIER));
        search.addProjectionOnField(new SimpleField(INTERACTOR_TYPE_MI_IDENTIFIER_STYLED));
        search.addProjectionOnField(new SimpleField(INTERACTION_COUNT));

        return solrOperations.queryForFacetAndHighlightPage(INTERACTORS, search, SearchInteractor.class);
    }

    public Criteria createFuzzySearchConditions(String searchTerm) {
        Criteria userConditions = null;
        searchTerm = "\"" + searchTerm + "\"";
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Criteria identifierCriteria = new Criteria(INTERACTOR_IDENTIFIERS).boost(10).expression(searchTerm);
            Criteria nameCriteria = new Criteria(INTERACTOR_NAME).boost(8).expression(searchTerm);
            Criteria aliasCriteria = new Criteria(INTERACTOR_ALIAS).boost(2).expression(searchTerm);
            Criteria descriptionCriteria = new Criteria(INTERACTOR_DESCRIPTION).expression(searchTerm);
            userConditions = identifierCriteria.or(nameCriteria).or(aliasCriteria).or(descriptionCriteria);
        }
        return userConditions;
    }

    public Criteria createExactSearchConditions(String searchTerm) {
        Criteria userConditions = null;
        searchTerm = "\"" + searchTerm + "\"";
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Criteria identifierCriteria = new Criteria(INTERACTOR_IDENTIFIERS).boost(10).expression(searchTerm);
            Criteria nameCriteria = new Criteria(INTERACTOR_NAME_STR).boost(8).expression(searchTerm);
            Criteria aliasCriteria = new Criteria(INTERACTOR_ALIAS_NAMES_STR).boost(2).expression(searchTerm);
            userConditions = identifierCriteria.or(nameCriteria).or(aliasCriteria);
        }
        return userConditions;
    }

    @Override
    public Page<SearchInteractor> findInteractorSuggestions(String query, Pageable pageable) {


        // search query
        SimpleQuery search = new SimpleQuery();

        // search criterias
        Criteria suggestionInteractorSearchCriteria = createSuggestionSearchConditions(query);
        search.addCriteria(suggestionInteractorSearchCriteria);

        // pagination
        search.setPageRequest(pageable);

        // sorting
        search.addSort(Sort.by(Sort.Direction.DESC, "score"));
        search.addSort(Sort.by(Sort.Direction.DESC, INTERACTION_COUNT));

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

        return solrOperations.queryForPage(INTERACTORS, search, SearchInteractor.class);
    }

    public Criteria createSuggestionSearchConditions(String searchTerm) {
        Criteria suggestionConditions = null;
        searchTerm = SearchInteractorUtils.escapeQueryChars(searchTerm);
        if (searchTerm != null && !searchTerm.isEmpty()) {

            // Keep this until we are sure we want to ignore the score calculated by solr and just assign constant score
            /*Criteria suggestCriteria = new Criteria(SUGGEST).boost(100).is(searchTerm);
            Criteria identifierCriteria = new Criteria(INTERACTOR_IDENTIFIER_DEFAULT).boost(15).is(searchTerm);
            Criteria nameCriteria = new Criteria(INTERACTOR_NAMES_DEFAULT).boost(8).is(searchTerm);
            Criteria aliasCriteria = new Criteria(INTERACTOR_ALIAS_DEFAULT).boost(2).is(searchTerm);
            Criteria defaultCriteria = new Criteria(DEFAULT).is(searchTerm);

            suggestionConditions = suggestCriteria.or(identifierCriteria).or(nameCriteria).or(aliasCriteria).or(defaultCriteria);

            */

            // to ignore scoring from solr and use just our constant scores
            // numbers are important here, changing them would break the order
            // suggest is for exact match
            // others for partial matches
            // ranking fields together with suggest (score gets added) cause exact match in ranking fields
            //         to get higher score than partial matches
            // any exact match with ranking fields should add up the score to be more than any partial match
            // two partial matches can be more than one exact match
            suggestionConditions = new SimpleStringCriteria(
                    "(" +
                            SUGGEST + ":" + searchTerm + "^=45.0" +
                            " OR " + INTERACTOR_IDENTIFIER_DEFAULT + ":" + searchTerm + "^=100.0" +
                            " OR " + INTERACTOR_NAMES_DEFAULT + ":" + searchTerm + "^=70.0" +
                            " OR " + INTERACTOR_ALIAS_DEFAULT + ":" + searchTerm + "^=65.0" +
                            " OR " + DEFAULT + ":" + searchTerm + ")");


        }
        return suggestionConditions;
    }
}