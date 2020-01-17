package uk.ac.ebi.intact.search.interactor.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.HashSet;
import java.util.Set;

import static uk.ac.ebi.intact.search.interactor.model.SearchInteractorFields.*;

/**
 * @author Elisabet Barrera
 */
@SolrDocument(collection = SearchInteractor.INTERACTORS)
public class SearchInteractor {

    public static final String INTERACTORS = "interactors";

    @Field(INTERACTOR_ID)
    private String interactorId;

    @Id
    @Field(INTERACTOR_AC)
    private String interactorAc;

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

    @Field(INTERACTOR_SPECIES_NAME)
    private String interactorSpecies;

    @Field(INTERACTOR_TAX_ID)
    private Integer interactorTaxId;

    @Field(INTERACTOR_XREFS)
    private Set<String> interactorXrefs;

    @Field(INTERACTION_COUNT)
    private Integer interactionCount;

    @Field(INTERACTION_IDS)
    private Set<String> interactionIds;

    @Field(INTERACTION_DETECTION_METHOD)
    private Set<String> interactionDetectionMethod;

    @Field(INTERACTION_TYPE)
    private Set<String> interactionType;

    @Field(INTERACTION_AC)
    private Set<String> interactionAc;

    @Field(INTERACTION_EXPANSION_METHOD)
    private Set<String> interactionExpansionMethod;

    @Field(INTERACTION_NEGATIVE)
    private Set<Boolean> interactionNegative;

    @Field(INTERACTION_MISCORE)
    private Set<Double> interactionMiScore;

    @Field(INTERACTION_HOST_ORGANISM)
    private Set<String> interactionHostOrganism;

    @Field(INTERACTOR_FEATURE_SHORTLABEL)
    private Set<String> interactorFeatureShortLabels;

    /** This field is not part of the solr doc.
     it is being added after a second call to interactions search service
     to know in how many interactions the interactor appear **/
    @Transient
    private Long interactionSearchCount;

    public SearchInteractor() {
    }

    public SearchInteractor(String interactorId, String interactorName, String description, Set<String> interactorAlias,
                            Set<String> interactorAltIds, String interactorType, String species, Integer taxId,
                            Set<String> interactorXrefs, Integer interactionCount, Long interactionSearchCount,
                            Set<String> interactionIds, Set<String> interactionDetectionMethod, Set<String> interactionType,
                            Set<String> interactionAc, Set<String> interactionExpansionMethod, Set<Boolean> interactionNegative,
                            Set<Double> interactionMiScore, Set<String> interactionHostOrganism,
                            Set<String> interactorFeatureShortLabels) {
        this.interactorId = interactorId;
        this.interactorName = interactorName;
        this.description = description;
        this.interactorAlias = interactorAlias;
        this.interactorAltIds = interactorAltIds;
        this.interactorType = interactorType;
        this.setInteractorSpecies(species);
        this.setInteractorTaxId(taxId);
        this.interactorXrefs = interactorXrefs;
        this.interactionCount = interactionCount;
        this.interactionSearchCount = interactionSearchCount;
        this.interactionIds = interactionIds;
        this.interactionDetectionMethod = interactionDetectionMethod;
        this.interactionType = interactionType;
        this.interactionAc = interactionAc;
        this.interactionExpansionMethod = interactionExpansionMethod;
        this.interactionNegative = interactionNegative;
        this.interactionMiScore = interactionMiScore;
        this.interactionHostOrganism = interactionHostOrganism;
        this.setInteractorFeatureShortLabels(interactorFeatureShortLabels);
    }

    public String getInteractorId() {
        return interactorId;
    }

    public void setInteractorId(String interactorId) {
        this.interactorId = interactorId;
    }

    public String getInteractorAc() {
        return interactorAc;
    }

    public void setInteractorAc(String interactorAc) {
        this.interactorAc = interactorAc;
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
        if (this.interactorAlias == null) {
            this.interactorAlias = new HashSet<>();
        }
        return interactorAlias;
    }

    public void setInteractorAlias(Set<String> interactorAlias) {
        this.interactorAlias = interactorAlias;
    }

    public Set<String> getInteractorAltIds() {
        if (this.interactorAltIds == null) {
            this.interactorAltIds = new HashSet<>();
        }
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

    public Set<String> getInteractorXrefs() {
        if (this.interactorXrefs == null) {
            this.interactorXrefs = new HashSet<>();
        }
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

    public Long getInteractionSearchCount() {
        return interactionSearchCount;
    }

    public void setInteractionSearchCount(Long interactionSearchCount) {
        this.interactionSearchCount = interactionSearchCount;
    }

    public Set<String> getInteractionIds() {
        if (this.interactorXrefs == null) {
            this.interactorXrefs = new HashSet<>();
        }
        return interactorXrefs;
    }

    public void setInteractionIds(Set<String> interactionIds) {
        this.interactionIds = interactionIds;
    }

    public Set<String> getInteractionDetectionMethod() {
        if (this.interactionDetectionMethod == null) {
            this.interactionDetectionMethod = new HashSet<>();
        }
        return interactionDetectionMethod;
    }

    public void setInteractionDetectionMethod(Set<String> interactionDetectionMethod) {
        this.interactionDetectionMethod = interactionDetectionMethod;
    }

    public Set<String> getInteractionType() {
        if (this.interactionType == null) {
            this.interactionType = new HashSet<>();
        }
        return interactionType;
    }

    public void setInteractionType(Set<String> interactionType) {
        this.interactionType = interactionType;
    }

    public Set<String> getInteractionAc() {
        if (this.interactionAc == null) {
            this.interactionAc = new HashSet<>();
        }
        return interactionAc;
    }

    public void setInteractionAc(Set<String> interactionAc) {
        this.interactionAc = interactionAc;
    }

    public Set<String> getInteractionExpansionMethod() {
        if (this.interactionExpansionMethod == null) {
            this.interactionExpansionMethod = new HashSet<>();
        }
        return interactionExpansionMethod;
    }

    public void setInteractionExpansionMethod(Set<String> interactionExpansionMethod) {
        this.interactionExpansionMethod = interactionExpansionMethod;
    }

    public Set<Boolean> getInteractionNegative() {
        if (this.interactionNegative == null) {
            this.interactionNegative = new HashSet<>();
        }
        return interactionNegative;
    }

    public void setInteractionNegative(Set<Boolean> interactionNegative) {
        this.interactionNegative = interactionNegative;
    }

    public Set<Double> getInteractionMiScore() {
        if (this.interactionMiScore == null) {
            this.interactionMiScore = new HashSet<>();
        }
        return interactionMiScore;
    }

    public void setInteractionMiScore(Set<Double> interactionMiScore) {
        this.interactionMiScore = interactionMiScore;
    }

    public Set<String> getInteractionHostOrganism() {
        if (this.interactionHostOrganism == null) {
            this.interactionHostOrganism = new HashSet<>();
        }
        return interactionHostOrganism;
    }

    public void setInteractionHostOrganism(Set<String> interactionHostOrganism) {
        this.interactionHostOrganism = interactionHostOrganism;
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
                ", species='" + getInteractorSpecies() + '\'' +
                ", taxId=" + getInteractorTaxId() +
                ", interactorXrefs=" + interactorXrefs +
                ", interactionCount=" + interactionCount +
                ", interactionSearchCount=" + interactionSearchCount +
                ", interactionIds=" + interactionIds +
                ", interactionDetectionMethod=" + interactionDetectionMethod +
                ", interactionType=" + interactionType +
                ", interactionAc=" + interactionAc +
                ", interactionExpansionMethod=" + interactionExpansionMethod +
                ", interactionNegative=" + interactionNegative +
                ", interactionMiScore=" + interactionMiScore +
                ", interactionHostOrganism=" + interactionHostOrganism +
                ", featureShortLabels=" + getInteractorFeatureShortLabels() +
                '}';
    }

    public String getInteractorSpecies() {
        return interactorSpecies;
    }

    public void setInteractorSpecies(String interactorSpecies) {
        this.interactorSpecies = interactorSpecies;
    }

    public Integer getInteractorTaxId() {
        return interactorTaxId;
    }

    public void setInteractorTaxId(Integer interactorTaxId) {
        this.interactorTaxId = interactorTaxId;
    }

    public Set<String> getInteractorFeatureShortLabels() {
        if (this.interactorFeatureShortLabels == null) {
            this.interactorFeatureShortLabels = new HashSet<>();
        }
        return interactorFeatureShortLabels;
    }

    public void setInteractorFeatureShortLabels(Set<String> interactorFeatureShortLabels) {
        this.interactorFeatureShortLabels = interactorFeatureShortLabels;
    }
}
