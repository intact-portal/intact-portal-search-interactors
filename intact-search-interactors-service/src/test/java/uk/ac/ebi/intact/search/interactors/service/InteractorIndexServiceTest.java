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
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author Elisabet Barrera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InteractorIndexServiceTest {

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
                new HashSet<>(Arrays.asList("interactor1_interaction_xref1", "interactor1_interaction_xref2", "interactor1_interaction_xref3"))
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
                new HashSet<>(Arrays.asList("interactor2_interaction_xref1", "interactor2_interaction_xref2", "interactor2_interaction_xref3"))
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
                new HashSet<>(Arrays.asList("interactor3_interaction_xref1", "interactor3_interaction_xref2", "interactor3_interaction_xref3"))
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
                new HashSet<>(Arrays.asList("interactor4_interaction_xref1", "interactor4_interaction_xref2", "interactor4_interaction_xref3"))
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
                new HashSet<>(Arrays.asList("interactor5_interaction_xref1", "interactor5_interaction_xref2", "interactor5_interaction_xref3"))
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
                new HashSet<>(Arrays.asList("interactor6_interaction_xref1", "interactor6_interaction_xref2", "interactor6_interaction_xref3"))
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
                new HashSet<>(Arrays.asList("interactor7_interaction_xref1", "interactor7_interaction_xref2", "interactor7_interaction_xref3"))
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
                new HashSet<>(Arrays.asList("interactor8_interaction_xref1", "interactor8_interaction_xref2", "interactor8_interaction_xref3"))
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
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")),
                new HashSet<>(Arrays.asList("interactor9_alias_names1", "interactor9_alias_names2", "interactor9_alias_names3", "exactmatchsimilar")),
                "interactor8_intact_name",
                new HashSet<>(Arrays.asList("interactor9_interaction_xref1", "interactor9_interaction_xref2", "interactor9_interaction_xref3"))
        );
    }

    @After
    public void tearDown() {
        interactorIndexService.deleteAll();
    }

    @Test
    public void triggerSchemaUpdateOnFirstSave() {

        interactorIndexService.save(searchInteractor1);

        Optional<SearchInteractor> interactor = interactorSearchService.findById("EBI-TEST1");
        assertEquals(interactor.get().getInteractorAc(), "EBI-TEST1");
        assertEquals(interactorSearchService.countTotal(), 1);
    }

    @Test
    public void triggerSchemaUpdateOnSaveCollection() {
        // empty collection
        interactorIndexService.deleteAll();

        interactorIndexService.saveAll(Arrays.asList(searchInteractor1, searchInteractor2, searchInteractor3));
        assertEquals(interactorSearchService.countTotal(), 3);
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
        interactorIndexService.deleteAll();
        interactorIndexService.saveAll(Arrays.asList(searchInteractor1, searchInteractor2, searchInteractor3, searchInteractor4, searchInteractor5, searchInteractor6, searchInteractor7, searchInteractor8, searchInteractor9));
        Page<SearchInteractor> suggestions = interactorSearchService.findInteractorSuggestions("exactmatch");
        assertEquals(interactorSearchService.countTotal(), 9);

        assertEquals(suggestions.getTotalElements(), 9);
        Iterator<SearchInteractor> searchInteractorIterator = suggestions.iterator();
        assertEquals(searchInteractorIterator.next().getInteractorName(), "4EBP1");//identifier exact match
        assertEquals(searchInteractorIterator.next().getInteractorName(), "interactorName5");// name exact match
        assertEquals(searchInteractorIterator.next().getInteractorName(), "interactorName6");// alias exact match
        assertEquals(searchInteractorIterator.next().getInteractorName(), "interactorName4");// other fields exact match
        assertEquals(searchInteractorIterator.next().getInteractorName(), "EIF4E");// identifier field partial match
        assertEquals(searchInteractorIterator.next().getInteractorName(), "SUMO1");// name field partial match
        assertEquals(searchInteractorIterator.next().getInteractorName(), "interactorName7");// alias field partial match
        assertEquals(searchInteractorIterator.next().getInteractorName(), "interactorName9");// other field partial match(more number of terms)
        assertEquals(searchInteractorIterator.next().getInteractorName(), "interactorName8");// other field partial match
    }


    @Test
    public void deleteCollection() {
        // empty collection
        interactorIndexService.deleteAll();
        assertEquals(interactorSearchService.countTotal(), 0);
    }

}