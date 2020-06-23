package uk.ac.ebi.intact.search.interactors.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

/**
 * Created by anjali on 13/02/20.
 */
@Repository
public interface CustomizedInteractorRepository {

    HighlightPage<SearchInteractor> resolveInteractor(String query, boolean fuzzySearch, Sort sort, Pageable pageable);
}
