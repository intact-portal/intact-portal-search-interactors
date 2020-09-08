package uk.ac.ebi.intact.search.interactors.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * @author Elisabet Barrera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InteractorSearchServiceTest {

    private SearchInteractor searchInteractor1;
    private SearchInteractor searchInteractor2;
    private SearchInteractor searchInteractor3;
    private SearchInteractor searchInteractor4;
    private SearchInteractor searchInteractor5;
    private SearchInteractor searchInteractor6;
    private SearchInteractor searchInteractor7;
    private SearchInteractor searchInteractor8;
    private SearchInteractor searchInteractor9;

    @Resource
    private InteractorIndexService interactorIndexService;

    @Resource
    private InteractorSearchService interactorSearchService;

    @Before
    public void setUp() {

        interactorIndexService.deleteAll();


        //Create new interactors documents
        searchInteractor1 = new SearchInteractor("EBI-TEST1",
                "EIF4E",
                "P06730",
                "Eukaryotic translation initiation factor 4E",
                new HashSet<>(Arrays.asList("interactor1_alias1", "interactor1_alias2", "interactor1_alias3")),
                new HashSet<>(Arrays.asList("interactor1_alt1", "interactor1_alt2", "interactor1_alt3", "exactmatchsimilar")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor1_xref1", "interactor1_xref2")),
                2,
                2L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2")),
                new HashSet<>(Arrays.asList("featureShortLabel1", "featureShortLabel2")),
                new HashSet<>(Arrays.asList("interactor1_alias_names1", "interactor1_alias_names2", "interactor1_alias_names3")),
                "interactor1_intact_name",
                new HashSet<>(Arrays.asList("interactor1_interaction_xref1", "interactor1_interaction_xref2", "interactor1_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor1_feature_type1", "interactor1_feature_type2", "interactor1_feature_type3"))
        );

        searchInteractor2 = new SearchInteractor("EBI-TEST2",
                "4EBP1",
                "Q13541",
                "Eukaryotic translation initiation factor 4E-binding protein 1",
                new HashSet<>(Arrays.asList("interactor2_alias1", "interactor2_alias1", "interactor2_alias3")),
                new HashSet<>(Arrays.asList("interactor2_alt1", "interactor2_alt1", "exactmatch")),
                "protein",
                "Mus musculus (mouse)",
                10090,
                new HashSet<>(Arrays.asList("interactor2_xref1", "interactor2_xref2")),
                2,
                1L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")),
                new HashSet<>(Arrays.asList("interactor1_alias_names1", "interactor1_alias_names2", "interactor1_alias_names3")),
                "interactor2_intact_name",
                new HashSet<>(Arrays.asList("interactor2_interaction_xref1", "interactor2_interaction_xref2", "interactor2_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor2_feature_type1", "interactor2_feature_type2", "interactor2_feature_type3"))
        );

        searchInteractor3 = new SearchInteractor("EBI-TEST3",
                "SUMO1",
                "P63165",
                "Small ubiquitin-related modifier 1",
                new HashSet<>(Arrays.asList("interactor3_alias1", "interactor3_alias1", "interactor3_alias3")),
                new HashSet<>(Arrays.asList("interactor3_alt1", "interactor3_alt1")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor3_xref1", "interactor3_xref2", "interactor3_xref3", "interactor3_xref4")),
                5,
                3L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")),
                new HashSet<>(Arrays.asList("interactor1_alias_names1", "interactor1_alias_names2", "interactor1_alias_names3")),
                "exactmatchsimilar",
                new HashSet<>(Arrays.asList("interactor3_interaction_xref1", "interactor3_interaction_xref2", "interactor3_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor3_feature_type1", "interactor3_feature_type2", "interactor3_feature_type3"))
        );

        searchInteractor4 = new SearchInteractor("EBI-TEST4",
                "interactorName4",
                "preferredIdentifier1",
                "exactmatch Description4",
                new HashSet<>(Arrays.asList("interactor4_alias1", "interactor4_alias1", "interactor4_alias3")),
                new HashSet<>(Arrays.asList("interactor4_alt1", "interactor4_alt1")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor4_xref1", "interactor4_xref2", "interactor4_xref3", "interactor4_xref4")),
                5,
                3L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")),
                new HashSet<>(Arrays.asList("interactor4_alias_names1", "interactor4_alias_names2", "interactor4_alias_names3")),
                "interactor4_intact_name",
                new HashSet<>(Arrays.asList("interactor4_interaction_xref1", "interactor4_interaction_xref2", "interactor4_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor4_feature_type1", "interactor4_feature_type2", "interactor4_feature_type3"))
        );

        searchInteractor5 = new SearchInteractor("EBI-TEST5",
                "interactorName5",
                "preferredIdentifier5",
                "Description5 exactmatch",
                new HashSet<>(Arrays.asList("interactor5_alias1", "interactor5_alias1", "interactor5_alias3")),
                new HashSet<>(Arrays.asList("interactor5_alt1", "interactor5_alt1")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor5_xref1", "interactor5_xref2", "interactor5_xref3", "interactor5_xref4")),
                5,
                3L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")),
                new HashSet<>(Arrays.asList("interactor5_alias_names1", "interactor5_alias_names2", "interactor5_alias_names3")),
                "exactmatch",
                new HashSet<>(Arrays.asList("interactor5_interaction_xref1", "interactor5_interaction_xref2", "interactor5_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor5_feature_type1", "interactor5_feature_type2", "interactor5_feature_type3"))
        );

        searchInteractor6 = new SearchInteractor("EBI-TEST6",
                "interactorName6",
                "preferredIdentifier6",
                "This is Description6 exactmatch",
                new HashSet<>(Arrays.asList("interactor6_alias1", "interactor6_alias1", "interactor6_alias3", "exactmatch")),
                new HashSet<>(Arrays.asList("interactor6_alt1", "interactor6_alt1")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor6_xref1", "interactor6_xref2", "interactor6_xref3", "interactor6_xref4")),
                5,
                3L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")),
                new HashSet<>(Arrays.asList("interactor6_alias_names1", "interactor6_alias_names2", "interactor6_alias_names3")),
                "interactor6_intact_name",
                new HashSet<>(Arrays.asList("interactor6_interaction_xref1", "interactor6_interaction_xref2", "interactor6_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor6_feature_type1", "interactor6_feature_type2", "interactor6_feature_type3"))
        );

        searchInteractor7 = new SearchInteractor("EBI-TEST7",
                "interactorName7",
                "preferredIdentifier7",
                "This is Description7",
                new HashSet<>(Arrays.asList("interactor7_alias1", "interactor7_alias1", "interactor7_alias3", "exactmatchsimilar")),
                new HashSet<>(Arrays.asList("interactor7_alt1", "interactor7_alt1")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor7_xref1", "interactor7_xref2", "interactor7_xref3", "interactor7_xref4")),
                5,
                3L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")),
                new HashSet<>(Arrays.asList("interactor7_alias_names1", "interactor7_alias_names2", "interactor7_alias_names3")),
                "interactor7_intact_name",
                new HashSet<>(Arrays.asList("interactor7_interaction_xref1", "interactor7_interaction_xref2", "interactor7_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor7_feature_type1", "interactor7_feature_type2", "interactor7_feature_type3"))
        );

        searchInteractor8 = new SearchInteractor("EBI-TEST8",
                "interactorName8",
                "preferredIdentifier8",
                "This is Description8 exactmatchsimilar",
                new HashSet<>(Arrays.asList("interactor8_alias1", "interactor8_alias1", "interactor8_alias3")),
                new HashSet<>(Arrays.asList("interactor8_alt1", "interactor8_alt1")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor8_xref1", "interactor8_xref2", "interactor8_xref3", "interactor8_xref4")),
                5,
                3L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")),
                new HashSet<>(Arrays.asList("interactor8_alias_names1", "interactor8_alias_names2", "interactor8_alias_names3")),
                "interactor8_intact_name",
                new HashSet<>(Arrays.asList("interactor8_interaction_xref1", "interactor8_interaction_xref2", "interactor8_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor8_feature_type1", "interactor8_feature_type2", "interactor8_feature_type3"))
        );

        searchInteractor9 = new SearchInteractor("EBI-TEST9",
                "interactorName9",
                "preferredIdentifier9",
                "This is Description9 exactmatchsimilar",
                new HashSet<>(Arrays.asList("interactor9_alias1", "interactor9_alias1", "interactor9_alias3")),
                new HashSet<>(Arrays.asList("interactor9_alt1", "interactor9_alt1")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor9_xref1", "interactor9_xref2", "interactor9_xref3", "interactor9_xref4")),
                5,
                3L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2", "exactmatchsimilar")),
                new HashSet<>(Arrays.asList("interactor9_alias_names1", "interactor9_alias_names2", "interactor9_alias_names3")),
                "interactor8_intact_name",
                new HashSet<>(Arrays.asList("interactor9_interaction_xref1", "interactor9_interaction_xref2", "interactor9_interaction_xref3")),
                new HashSet<>(Arrays.asList("interactor9_feature_type1", "interactor9_feature_type2", "interactor9_feature_type3"))
        );

        interactorIndexService.deleteAll();
        interactorIndexService.saveAll(Arrays.asList(searchInteractor1, searchInteractor2, searchInteractor3, searchInteractor4, searchInteractor5, searchInteractor6, searchInteractor7, searchInteractor8, searchInteractor9));
        assertEquals(interactorSearchService.countTotal(), 9);
    }

    @After
    public void tearDown() {
        interactorIndexService.deleteAll();
    }

    /*
     * Suggestion order test
     * 1.exact match in identifier,name,aliases in the order stated here
     * 2.exact match in other fields
     * 3.partial matches in identifier,name,aliases in the order stated here
     * 4.The order will not change if term was found in more than one times in ranking fields in a document
     * */
    @Test
    public void suggestionOrderTest() {
        // empty collection
        Page<SearchInteractor> suggestions = interactorSearchService.findInteractorSuggestions("exactmatch");
        assertEquals(interactorSearchService.countTotal(), 9);

        assertEquals(suggestions.getTotalElements(), 9);
        Iterator<SearchInteractor> searchInteractorIterator = suggestions.iterator();
        assertEquals("4EBP1", searchInteractorIterator.next().getInteractorName());//identifier exact match
        assertEquals("interactorName5", searchInteractorIterator.next().getInteractorName());// name exact match
        assertEquals("interactorName6", searchInteractorIterator.next().getInteractorName());// alias exact match
        assertEquals("EIF4E", searchInteractorIterator.next().getInteractorName());// identifier field partial match
        assertEquals("SUMO1", searchInteractorIterator.next().getInteractorName());// name field partial match
        assertEquals("interactorName7", searchInteractorIterator.next().getInteractorName());// alias field partial match
        assertEquals("interactorName4", searchInteractorIterator.next().getInteractorName());// other fields exact match
        assertEquals("interactorName9", searchInteractorIterator.next().getInteractorName());// other field partial match(more number of terms)
        assertEquals("interactorName8", searchInteractorIterator.next().getInteractorName());// other field partial match
    }

    /**
     * Behaviour If the User types "SearchInteractor Interactor Intact name" in search box
     */
    @Test
    public void findByInteractorIntactName() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("interactor1_intact_name");
        assertEquals(1, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "SearchInteractor Interactor Full name" in search box
     */
    @Test
    public void findByInteractorFullName() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("\"Eukaryotic translation\"");
        assertEquals(2, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "SearchInteractor Interactor Xref" in search box
     * includes preserve original test
     */
    @Test
    public void findByInteractorXref() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("interactor7_xref1");
        assertEquals(1, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "SearchInteractor Interactor Aliases" in search box
     */
    @Test
    public void findByInteractorAliases() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("interactor1_alias1");
        assertEquals(1, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "SearchInteractor Interaction Xref" in search box
     */
    @Test
    public void findByInteractionXref() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("interaction1");
        assertEquals(9, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "SearchInteractor Feature ShortLabel" in search box
     */
    @Test
    public void findByfeatureShortLabel() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("featureshortlabel1");
        assertEquals(9, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "SearchInteractor Feature Type" in search box
     */
    @Test
    public void findByfeatureType() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("interactor4_feature_type1");
        assertEquals(1, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "middle chunk of a word with delimiter" in search box
     */
    @Test
    public void edgeNGramsTest1() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("or1_al");
        assertEquals(0, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "middle chunk of a word "in search box
     */
    @Test
    public void edgeNGramsTest2() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("nteractor");
        assertEquals(0, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "word separated by delimiter" in search box
     */
    @Test
    public void wordPartIndexTimeGeneration() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("interactor2");
        assertEquals(1, interactionOp.getTotalElements());
    }

    /**
     * Behaviour If the User types "concatenation after removing delimiter" in search box
     */
    @Test
    public void concatenationOfDelimitedTerm() {
        Page<SearchInteractor> interactionOp = interactorSearchService.findInteractorSuggestions("interactor2xref1");
        assertEquals(1, interactionOp.getTotalElements());
    }


}