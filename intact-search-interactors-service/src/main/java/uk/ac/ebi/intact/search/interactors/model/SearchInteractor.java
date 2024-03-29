package uk.ac.ebi.intact.search.interactors.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.HashSet;
import java.util.Set;

import static uk.ac.ebi.intact.search.interactors.model.SearchInteractorFields.*;

/**
 * @author Elisabet Barrera
 */
@SolrDocument(collection = SearchInteractor.INTERACTORS)
public class SearchInteractor {

    public static final String INTERACTORS = "interactors";

    @Id
    @Field(INTERACTOR_AC)
    private String interactorAc;

    @Field(INTERACTOR_NAME)
    /* Prefer name of the interactor */
    private String interactorName;

    @Field(INTERACTOR_INTACT_NAME)
    /* Intact name/shortlabel of the interactor */
    private String interactorIntactName;

    @Field(INTERACTOR_PREFERRED_ID)
    /* Prefer identifier of the interactor */
    private String interactorPreferredIdentifier;

    @Field(INTERACTOR_DESCRIPTION)
    private String interactorDescription;

    @Field(INTERACTOR_ALIAS)
    private Set<String> interactorAlias;

    @Field(INTERACTOR_ALIAS_NAMES)
    private Set<String> interactorAliasNames;

    @Field(INTERACTOR_ALT_IDS)
    private Set<String> interactorAltIds;

    @Field(INTERACTOR_TYPE)
    private String interactorType;

    @Field(INTERACTOR_TYPE_MI_IDENTIFIER)
    private String interactorTypeMIIdentifier;

    @Field(INTERACTOR_TYPE_MI_IDENTIFIER_STYLED)
    private String interactorTypeMIIdentifierStyled;

    @Field(INTERACTOR_SPECIES_NAME)
    private String interactorSpecies;

    @Field(INTERACTOR_TAX_ID)
    private Integer interactorTaxId;

    @Field(INTERACTOR_TAX_ID_STYLED)
    private String interactorTaxIdStyled;

    @Field(INTERACTOR_XREFS)
    private Set<String> interactorXrefs;

    @Field(INTERACTION_COUNT)
    private Integer interactionCount;

    @Field(INTERACTION_IDS)
    private Set<String> interactionIds;

    @Field(INTERACTION_XREFS)
    private Set<String> interactionXrefs;

    @Field(INTERACTOR_FEATURE_SHORTLABELS)
    private Set<String> interactorFeatureShortLabels;

    @Field(INTERACTOR_FEATURE_TYPES)
    private Set<String> interactorFeatureTypes;

    /** Maybe we can stored directly in solr the color and shape without connecting to taxId or interactor type **/
    @Transient
    private String interactorColor;

    @Transient
    private String interactorShape;

    /**
     * This field is not part of the solr doc.
     * it is being added after a second call to interactions search service
     * to know in how many interactions the interactor appear
     **/
    @Transient
    private Long interactionSearchCount;

    public SearchInteractor() {
    }

    public SearchInteractor(String interactorAc,
                            String interactorName,
                            String interactorPreferredIdentifier,
                            String interactorDescription,
                            Set<String> interactorAlias,
                            Set<String> interactorAltIds,
                            String interactorType,
                            String interactorSpecies,
                            Integer interactorTaxId,
                            Set<String> interactorXrefs,
                            Integer interactionCount,
                            Long interactionSearchCount,
                            Set<String> interactionIds,
                            Set<String> interactorFeatureShortLabels,
                            Set<String> interactorAliasNames,
                            String interactorIntactName,
                            Set<String> interactionXrefs,
                            Set<String> interactorFeatureTypes,
                            String interactorTypeMIIdentifier) {
        this.interactorAc = interactorAc;
        this.interactorName = interactorName;
        this.interactorPreferredIdentifier = interactorPreferredIdentifier;
        this.interactorDescription = interactorDescription;
        this.interactorAlias = interactorAlias;
        this.interactorAliasNames = interactorAliasNames;
        this.interactorAltIds = interactorAltIds;
        this.interactorType = interactorType;
        this.interactorSpecies = interactorSpecies;
        this.interactorTaxId = interactorTaxId;
        this.interactorXrefs = interactorXrefs;
        this.interactionCount = interactionCount;
        this.interactionSearchCount = interactionSearchCount;
        this.interactionIds = interactionIds;
        this.interactorIntactName = interactorIntactName;
        this.interactionXrefs = interactionXrefs;
        this.interactorFeatureTypes = interactorFeatureTypes;
        this.interactorFeatureShortLabels = interactorFeatureShortLabels;
        this.interactorTypeMIIdentifier = interactorTypeMIIdentifier;
    }

    public String getInteractorTypeMIIdentifier() {
        return interactorTypeMIIdentifier;
    }

    public void setInteractorTypeMIIdentifier(String interactorTypeMIIdentifier) {
        this.interactorTypeMIIdentifier = interactorTypeMIIdentifier;
    }

    public String getInteractorTypeMIIdentifierStyled() {
        return interactorTypeMIIdentifierStyled;
    }

    public void setInteractorTypeMIIdentifierStyled(String interactorTypeMIIdentifierStyled) {
        this.interactorTypeMIIdentifierStyled = interactorTypeMIIdentifierStyled;
    }

    public Set<String> getInteractorFeatureTypes() {
        return interactorFeatureTypes;
    }

    public void setInteractorFeatureTypes(Set<String> interactorFeatureTypes) {
        this.interactorFeatureTypes = interactorFeatureTypes;
    }

    public String getInteractorIntactName() {
        return interactorIntactName;
    }

    public void setInteractorIntactName(String interactorIntactName) {
        this.interactorIntactName = interactorIntactName;
    }

    public Set<String> getInteractionXrefs() {
        return interactionXrefs;
    }

    public void setInteractionXrefs(Set<String> interactionXrefs) {
        this.interactionXrefs = interactionXrefs;
    }

    public Set<String> getInteractorAliasNames() {
        return interactorAliasNames;
    }

    public void setInteractorAliasNames(Set<String> interactorAliasNames) {
        this.interactorAliasNames = interactorAliasNames;
    }

    public String getInteractorAc() {
        return interactorAc;
    }

    public void setInteractorAc(String interactorAc) {
        this.interactorAc = interactorAc;
    }

    public String getInteractorDescription() {
        return interactorDescription;
    }

    public void setInteractorDescription(String interactorDescription) {
        this.interactorDescription = interactorDescription;
    }

    public String getInteractorName() {
        return interactorName;
    }

    public void setInteractorName(String interactorName) {
        this.interactorName = interactorName;
    }

    public String getInteractorPreferredIdentifier() {
        return interactorPreferredIdentifier;
    }

    public void setInteractorPreferredIdentifier(String interactorPreferredIdentifier) {
        this.interactorPreferredIdentifier = interactorPreferredIdentifier;
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
        if (this.interactionIds == null) {
            this.interactionIds = new HashSet<>();
        }
        return interactionIds;
    }

    public void setInteractionIds(Set<String> interactionIds) {
        this.interactionIds = interactionIds;
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

    public String getInteractorTaxIdStyled() {
        return interactorTaxIdStyled;
    }

    public void setInteractorTaxIdStyled(String interactorTaxIdStyled) {
        this.interactorTaxIdStyled = interactorTaxIdStyled;
    }

    public void setInteractorFeatureShortLabels(Set<String> interactorFeatureShortLabels) {
        this.interactorFeatureShortLabels = interactorFeatureShortLabels;
    }

    public String getInteractorColor() {
        if(this.interactorTaxIdStyled != null) {
            String[] styledValue = this.interactorTaxIdStyled.split("__");
            if (styledValue.length == 3) {
                interactorColor = styledValue[2];
            }
        }
        return interactorColor;
    }


    public String getInteractorShape() {
        if(this.interactorTypeMIIdentifierStyled != null) {
            String[] styledValue = this.interactorTypeMIIdentifierStyled.split("__");
            if (styledValue.length == 3) {
                interactorShape = styledValue[2];
            }
        }
        return interactorShape;
    }

    @Override
    public String toString() {
        return "SearchInteractor{" +
                "interactorAc='" + interactorAc + '\'' +
                ", interactorName='" + interactorName + '\'' +
                ", interactorIntactName='" + interactorIntactName + '\'' +
                ", interactorPreferredIdentifier='" + interactorPreferredIdentifier + '\'' +
                ", interactorDescription='" + interactorDescription + '\'' +
                ", interactorAlias=" + interactorAlias +
                ", interactorAliasNames=" + interactorAliasNames +
                ", interactorAltIds=" + interactorAltIds +
                ", interactorType='" + interactorType + '\'' +
                ", interactorSpecies='" + interactorSpecies + '\'' +
                ", interactorTaxId=" + interactorTaxId +
                ", interactorXrefs=" + interactorXrefs +
                ", interactionCount=" + interactionCount +
                ", interactionIds=" + interactionIds +
                ", interactionXrefs=" + interactionXrefs +
                ", interactorFeatureShortLabels=" + interactorFeatureShortLabels +
                ", interactorFeatureTypes=" + interactorFeatureTypes +
                ", interactionSearchCount=" + interactionSearchCount +
                '}';
    }
}