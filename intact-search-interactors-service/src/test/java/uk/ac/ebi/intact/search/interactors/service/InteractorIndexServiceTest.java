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
        searchInteractor1 = new SearchInteractor("P06730",
                "Eukaryotic translation initiation factor 4E",
                "EIF4E",
                new HashSet<>(Arrays.asList("interactor1_alias1", "interactor1_alias2", "interactor1_alias3")),
                new HashSet<>(Arrays.asList("interactor1_alt1", "interactor1_alt2", "interactor1_alt3")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor1_xref1", "interactor1_xref2")),
                2,
                2L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2")),
                new HashSet<>(Arrays.asList("detectionMethod1", "detectionMethod2")),
                new HashSet<>(Arrays.asList("interactionType1", "interactionType2")),
                new HashSet<>(Arrays.asList("expansionMethod1", "expansionMethod2")),
                new HashSet<>(Arrays.asList(true, true, false)),
                new HashSet<>(Arrays.asList(0.33, 0.52)),
                new HashSet<>(Arrays.asList("in vitro", "Saccharomyces cerevisiae")),
                new HashSet<>(Arrays.asList("featureShortLabel1", "featureShortLabel2"))
        );

        searchInteractor2 = new SearchInteractor("Q13541",
                "Eukaryotic translation initiation factor 4E-binding protein 1",
                "4EBP1",
                new HashSet<>(Arrays.asList("interactor2_alias1", "interactor2_alias1", "interactor2_alias3")),
                new HashSet<>(Arrays.asList("interactor2_alt1", "interactor2_alt1")),
                "protein",
                "Mus musculus (mouse)",
                10090,
                new HashSet<>(Arrays.asList("interactor2_xref1", "interactor2_xref2")),
                2,
                1L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2")),
                new HashSet<>(Arrays.asList("detectionMethod1", "detectionMethod2")),
                new HashSet<>(Arrays.asList("interactionType1", "interactionType2")),
                new HashSet<>(Arrays.asList("expansionMethod1", "expansionMethod2")),
                new HashSet<>(Arrays.asList(true, true, false)),
                new HashSet<>(Arrays.asList(0.33, 0.52)),
                new HashSet<>(Arrays.asList("in vitro", "Saccharomyces cerevisiae")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")));

        searchInteractor3 = new SearchInteractor("P63165",
                "Small ubiquitin-related modifier 1",
                "SUMO1",
                new HashSet<>(Arrays.asList("interactor3_alias1", "interactor3_alias1", "interactor3_alias3")),
                new HashSet<>(Arrays.asList("interactor3_alt1", "interactor3_alt1")),
                "protein",
                "Homo sapiens (Human)",
                9606,
                new HashSet<>(Arrays.asList("interactor3_xref1", "interactor3_xref2", "interactor3_xref3", "interactor3_xref4")),
                5,
                3L,
                new HashSet<>(Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5")),
                new HashSet<>(Arrays.asList("detectionMethod1", "detectionMethod2")),
                new HashSet<>(Arrays.asList("interactionType1", "interactionType2")),
                new HashSet<>(Arrays.asList("expansionMethod1", "expansionMethod2")),
                new HashSet<>(Arrays.asList(true, true, false)),
                new HashSet<>(Arrays.asList(0.33, 0.52)),
                new HashSet<>(Arrays.asList("Saccharomyces cerevisiae", "Homo sapiens")),
                new HashSet<>(Arrays.asList("featureshortlabel1", "featureshortlabel2")));
    }

    @After
    public void tearDown() {
        interactorIndexService.deleteAll();
    }

    @Test
    public void triggerSchemaUpdateOnFirstSave() {

        interactorIndexService.save(searchInteractor1);

        Optional<SearchInteractor> interactor = interactorSearchService.findById("P06730");
        assertEquals(interactor.get().getInteractorAc(), "P06730");
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