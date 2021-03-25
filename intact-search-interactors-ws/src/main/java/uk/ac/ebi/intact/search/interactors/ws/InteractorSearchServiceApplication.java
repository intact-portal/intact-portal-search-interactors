package uk.ac.ebi.intact.search.interactors.ws;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import uk.ac.ebi.intact.style.mapper.ontology.archetypes.Archetype;
import uk.ac.ebi.intact.style.model.serializer.ArchetypeSerializer;
import uk.ac.ebi.intact.style.model.serializer.ColorDeserializer;
import uk.ac.ebi.intact.style.model.serializer.ColorSerializer;

import java.awt.*;

@EnableSolrRepositories(basePackages = "uk.ac.ebi.intact.search",
		schemaCreationSupport = true)
@SpringBootApplication(scanBasePackages = {"uk.ac.ebi.intact"})
public class InteractorSearchServiceApplication extends SpringBootServletInitializer {

	@Value("${spring.data.solr.host}")
	private String solrHost;

	//This enables the option to pack the app as a .war for external tomcats
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InteractorSearchServiceApplication.class);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonSerialization() {
		return mapperBuilder -> {
			mapperBuilder.deserializerByType(Color.class, new ColorDeserializer());
			mapperBuilder.serializerByType(Color.class, new ColorSerializer());
			mapperBuilder.serializerByType(Archetype.class, new ArchetypeSerializer());
		};
	}

	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder(solrHost).build();
	}

	@Bean
	public SolrOperations solrTemplate() {
		return new SolrTemplate(solrClient());
	}

	public static void main(String[] args) {
		SpringApplication.run(InteractorSearchServiceApplication.class, args);
	}
}
