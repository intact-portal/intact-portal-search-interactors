package uk.ac.ebi.intact.search.interactor.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.lang.Nullable;

import java.util.Set;

import static uk.ac.ebi.intact.search.interactor.model.SearchInteractorFields.*;

/**
 * @author Elisabet Barrera
 */
@SolrDocument(solrCoreName = SearchInteractor.INTERACTORS)
public class SearchInteractor {

    public static final String INTERACTORS = "interactors";

    @Id
    @Field(INTERACTOR_ID)
    @Indexed
    private String interactorId;

    @Field(INTERACTOR_NAME)
    private String interactorName;

    @Field(INTERACTOR_DESCRIPTION)
    private String description;

    @Field(INTERACTOR_ALIAS)
    private Set<String> interactorAlias;

    @Field(INTERACTOR_ALT_IDS)
    private Set<String> interactorAltIds;

    @Field(INTERACTOR_TYPE)
    private String interactorType;

    @Field(SPECIES_NAME)
    private String species;

    @Field(TAX_ID)
    private Integer taxId;

    @Field(INTERACTOR_XREFS)
    private Set<String> interactorXrefs;

    @Field(INTERACTION_COUNT)
    private Integer interactionCount;

    @Field(INTERACTION_IDS)
    private Set<String> interactionIds;

    @Field(FEATURE_SHORTLABEL)
    @Nullable
    private Set<String> featureShortLabels;

    public SearchInteractor() {
    }

    @Override
    public String toString() {
        return "SearchInteractor{" +
                "interactorId='" + interactorId + '\'' +
                ", interactorName='" + interactorName + '\'' +
                ", description='" + description + '\'' +
                ", interactorAlias=" + interactorAlias +
                ", interactorAltIds=" + interactorAltIds +
                ", interactorType='" + interactorType + '\'' +
                ", species='" + species + '\'' +
                ", taxId=" + taxId +
                ", interactorXrefs=" + interactorXrefs +
                ", interactionCount=" + interactionCount +
                ", interactionIds=" + interactionIds +
                ", featureShortLabels=" + featureShortLabels +
                '}';
    }

    public SearchInteractor(String interactorId, String interactorName, String description, Set<String> interactorAlias, Set<String> interactorAltIds, String interactorType, String species, Integer taxId, Set<String> interactorXrefs, Integer interactionCount, Set<String> interactionIds, Set<String> featureShortLabels) {
        this.interactorId = interactorId;
        this.interactorName = interactorName;
        this.description = description;
        this.interactorAlias = interactorAlias;
        this.interactorAltIds = interactorAltIds;
        this.interactorType = interactorType;
        this.species = species;
        this.taxId = taxId;
        this.interactorXrefs = interactorXrefs;
        this.interactionCount = interactionCount;
        this.interactionIds = interactionIds;
        this.featureShortLabels = featureShortLabels;
    }

    public String getInteractorId() {
        return interactorId;
    }

    public void setInteractorId(String interactorId) {
        this.interactorId = interactorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInteractorName() {
        return interactorName;
    }

    public void setInteractorName(String interactorName) {
        this.interactorName = interactorName;
    }

    public Set<String> getInteractorAlias() {
        return interactorAlias;
    }

    public void setInteractorAlias(Set<String> interactorAlias) {
        this.interactorAlias = interactorAlias;
    }

    public Set<String> getInteractorAltIds() {
        return interactorAltIds;
    }

    public void setInteractorAltIds(Set<String> interactorAltIds) {
        this.interactorAltIds = interactorAltIds;
    }

    public String getInteractorType() {
        return interactorType;
    }

    public void setInteractorType(String interactorType) {
        this.interactorType = interactorType;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public Set<String> getInteractorXrefs() {
        return interactorXrefs;
    }

    public void setInteractorXrefs(Set<String> interactorXrefs) {
        this.interactorXrefs = interactorXrefs;
    }

    public Integer getInteractionCount() {
        return interactionCount;
    }

    public void setInteractionCount(Integer interactionCount) {
        this.interactionCount = interactionCount;
    }

    public Set<String> getInteractionIds() {
        return interactionIds;
    }

    public void setInteractionIds(Set<String> interactionIds) {
        this.interactionIds = interactionIds;
    }

    public Set<String> getFeatureShortLabels() {
        return featureShortLabels;
    }

    public void setFeatureShortLabels(Set<String> featureShortLabels) {
        this.featureShortLabels = featureShortLabels;
    }
}
