package uk.ac.ebi.intact.search.interactors.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
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
                new HashSet<>(Arrays.asList("interactor1_alt1", "interactor1_alt2", "interactor1_alt3")),
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
                new HashSet<>(Arrays.asList("interactor2_alt1", "interactor2_alt1")),
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
                "interactor3_intact_name",
                new HashSet<>(Arrays.asList("interactor3_interaction_xref1", "interactor3_interaction_xref2", "interactor3_interaction_xref3"))
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


    @Test
    public void deleteCollection() {
        // empty collection
        interactorIndexService.deleteAll();
        assertEquals(interactorSearchService.countTotal(), 0);
    }

}