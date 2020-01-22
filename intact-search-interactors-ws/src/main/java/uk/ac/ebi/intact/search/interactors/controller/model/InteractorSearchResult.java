package uk.ac.ebi.intact.search.interactors.controller.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.Field;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;

import java.util.*;
import java.util.function.Function;

public class InteractorSearchResult implements Page<SearchInteractor> {

    private final FacetPage<SearchInteractor> page;

    public InteractorSearchResult(FacetPage<SearchInteractor> page) {
        this.page = page;
    }

    @Override
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    public int getSize() {
        return page.getSize();
    }

    @Override
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    public List<SearchInteractor> getContent() {
        return page.getContent();
    }

    @Override
    public boolean hasContent() {
        return page.hasContent();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return page.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return page.previousPageable();
    }

    @Override
    public <U> Page<U> map(Function<? super SearchInteractor, ? extends U> converter) {
        return page.map(converter);
    }

    @Override
    public Iterator<SearchInteractor> iterator() {
        return page.iterator();
    }

    public Collection<Field> getFacetFields() {
        return page.getFacetFields();
    }

    public Map<String, List<FacetCount>> getFacetResultPage() {
        Map<String, List<FacetCount>> facetPerFieldMap = new HashMap<>();

        for (Field field : page.getFacetFields()) {
            List<FacetCount> facet = new ArrayList<>();
            for (FacetFieldEntry facetFieldEntry : page.getFacetResultPage(field).getContent()) {
                facet.add(new FacetCount(facetFieldEntry.getValue(), facetFieldEntry.getValueCount()));
            }
            facetPerFieldMap.put(field.getName(), facet);
        }

        return facetPerFieldMap;
    }

    public Map<String,Map<String,Long>> getFacetResultPage2() {
        Map<String,Map<String,Long>> facetPerFieldMap = new HashMap<>();

        for (Field field : page.getFacetFields()) {
            Map<String,Long> facet = new HashMap<>();
            for (FacetFieldEntry facetFieldEntry : page.getFacetResultPage(field).getContent()) {
                facet.put(facetFieldEntry.getValue(),facetFieldEntry.getValueCount());
            }
            facetPerFieldMap.put(field.getName(), facet);
        }

        return facetPerFieldMap;
    }

    private class FacetCount {

        private String value;
        private Long valueCount;

        FacetCount(String value, Long valueCount) {
            this.value = value;
            this.valueCount = valueCount;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Long getValueCount() {
            return valueCount;
        }

        public void setValueCount(Long valueCount) {
            this.valueCount = valueCount;
        }
    }
}
