package uk.ac.ebi.intact.search.interactor.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

/**
 * @author Elisabet Barrera
 */
@SolrDocument(solrCoreName = "gettingstarted")
public class Interactor {

    @Id
    @Field("interactor_id")
    @Indexed
    private String interactorId;

    @Field("interactor_name")
    private String interactorName;

    @Field("description")
    private String description;

    @Field("interactor_alias")
    private List<String> interactorAlias;

    @Field("interactor_alt_ids")
    private List<String> interactorAltIds;

    @Field("interactor_type")
    private String interactorType;

    @Field("species_name")
    private String species;

    @Field("tax_id")
    private Integer taxId;

    @Field("interactor_xrefs")
    private List<String> interactorXrefs;

    @Field("interaction_count")
    private Integer interactionCount;

    @Field("interaction_ids")
    private List<String> interactionIds;

    public Interactor() {
    }

    public Interactor(String interactorId, String description, String interactorName, List<String> interactorAlias,
                      List<String> interactorAltIds, String interactorType, String species, Integer taxId,
                      List<String> interactorXrefs, Integer interactionCount, List<String> interactionIds) {
        this.interactorId = interactorId;
        this.description = description;
        this.interactorName = interactorName;
        this.interactorAlias = interactorAlias;
        this.interactorAltIds = interactorAltIds;
        this.interactorType = interactorType;
        this.species = species;
        this.taxId = taxId;
        this.interactorXrefs = interactorXrefs;
        this.interactionCount = interactionCount;
        this.interactionIds = interactionIds;
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

    public List<String> getInteractorAlias() {
        return interactorAlias;
    }

    public void setInteractorAlias(List<String> interactorAlias) {
        this.interactorAlias = interactorAlias;
    }

    public List<String> getInteractorAltIds() {
        return interactorAltIds;
    }

    public void setInteractorAltIds(List<String> interactorAltIds) {
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

    public List<String> getInteractorXrefs() {
        return interactorXrefs;
    }

    public void setInteractorXrefs(List<String> interactorXrefs) {
        this.interactorXrefs = interactorXrefs;
    }

    public Integer getInteractionCount() {
        return interactionCount;
    }

    public void setInteractionCount(Integer interactionCount) {
        this.interactionCount = interactionCount;
    }

    public List<String> getInteractionIds() {
        return interactionIds;
    }

    public void setInteractionIds(List<String> interactionIds) {
        this.interactionIds = interactionIds;
    }

    @Override
    public String toString() {
        return "Interactor{" +
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
                '}';
    }
}
