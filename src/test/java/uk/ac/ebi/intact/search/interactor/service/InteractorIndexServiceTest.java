package uk.ac.ebi.intact.search.interactor.service;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.intact.search.interactor.model.Interactor;
import uk.ac.ebi.intact.search.interactor.service.util.RequiresSolrServer;

import javax.annotation.Resource;

import java.util.Arrays;

/**
 * @author Elisabet Barrera
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {SolrTestConfiguration.class})
@RunWith(SpringRunner.class)
@SpringBootTest
public class InteractorIndexServiceTest {

    private Interactor interactor1;
    private Interactor interactor2;
    private Interactor interactor3;

    @Resource
    private InteractorIndexService interactorIndexService;

    @Resource
    private InteractorSearchService interactorSearchService;

    public static @ClassRule
    RequiresSolrServer requiresRunningServer = RequiresSolrServer.onLocalhost();

//    @Resource
//    SolrTemplate solrTemplate;

    @Before
    public void setUp() throws Exception {

        //Delete all documents from solr core
        interactorIndexService.deleteAll();

        //Create new interactors documents
        interactor1 = new Interactor("P06730",
                "Eukaryotic translation initiation factor 4E",
                "EIF4E",
                Arrays.asList("interactor1_alias1", "interactor1_alias2", "interactor1_alias3"),
                Arrays.asList("interactor1_alt1", "interactor1_alt2", "interactor1_alt3"),
                "protein",
                "Homo sapiens (Human)",
                9606,
                Arrays.asList("interactor1_xref1", "interactor1_xref2"),
                2,
                Arrays.asList("interaction1", "interaction2"));

        interactor2 = new Interactor("Q13541",
                "Eukaryotic translation initiation factor 4E-binding protein 1",
                "4EBP1",
                Arrays.asList("interactor2_alias1", "interactor2_alias1", "interactor2_alias3"),
                Arrays.asList("interactor2_alt1", "interactor2_alt1"),
                "protein",
                "Mus musculus (mouse)",
                10090,
                Arrays.asList("interactor2_xref1", "interactor2_xref2"),
                2,
                Arrays.asList("interaction1", "interaction2"));

        interactor3 = new Interactor("P63165",
                "Small ubiquitin-related modifier 1",
                "SUMO1",
                Arrays.asList("interactor3_alias1", "interactor3_alias1", "interactor3_alias3"),
                Arrays.asList("interactor3_alt1", "interactor3_alt1"),
                "protein",
                "Homo sapiens (Human)",
                9606,
                Arrays.asList("interactor3_xref1", "interactor3_xref2", "interactor3_xref3", "interactor3_xref4"),
                5,
                Arrays.asList("interaction1", "interaction2", "interaction3", "interaction4", "interaction5"));
    }

    @After
    public void tearDown() throws Exception {
//        solrOperations.delete(new SimpleQuery(new SimpleStringCriteria("*:*")));
//        solrOperations.commit();
    }

    @Test
    public void triggerSchemaUpdateOnFirstSave() {

        interactorIndexService.save(interactor1);

        Interactor itr = interactorSearchService.findBy("P06730");



    }

    @Test
    public void triggerSchemaUpdateOnSaveCollection() {
        // empty collection
        interactorIndexService.deleteAll();

        interactorIndexService.save(Arrays.asList(interactor1, interactor2, interactor3));

    }


    @Test
    public void deleteCollection() {
        // empty collection
        interactorIndexService.deleteAll();


    }

}