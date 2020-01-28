package uk.ac.ebi.intact.search.interactors.service;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;

/**
 * @author Elisabet Barrera
 */
@TestConfiguration
@EnableSolrRepositories(basePackages = "uk.ac.ebi.intact.search.interactors.repository",
        schemaCreationSupport = true)
@SpringBootApplication
public class SolrTestConfiguration {

    @Bean
    public SolrClient solrClient() throws Exception {
        EmbeddedSolrServerFactory factory = new EmbeddedSolrServerFactory("src/test/resources/solr.home.7.3.1");
        return factory.getSolrClient();
        //return new HttpSolrClient.Builder("http://localhost:8983/solr").build();

    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client)  {
        return new SolrTemplate(client);
    }
}
