package uk.ac.ebi.intact.search.interactors.ws.controller.model;

import uk.ac.ebi.intact.search.interactors.model.SearchInteractor;
import uk.ac.ebi.intact.style.model.shapes.NodeShape;

import java.awt.*;
import java.util.Set;

public class StyledSearchInteractor extends SearchInteractor {

    private Color color;
    private NodeShape shape;

    public StyledSearchInteractor(String interactorAc,
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
                                  String interactorTypeMIIdentifier,
                                  Color color,
                                  NodeShape shape) {
        super(interactorAc,
                interactorName,
                interactorPreferredIdentifier,
                interactorDescription,
                interactorAlias,
                interactorAltIds,
                interactorType,
                interactorSpecies,
                interactorTaxId,
                interactorXrefs,
                interactionCount,
                interactionSearchCount,
                interactionIds,
                interactorFeatureShortLabels,
                interactorAliasNames,
                interactorIntactName,
                interactionXrefs,
                interactorFeatureTypes,
                interactorTypeMIIdentifier);
        this.color = color;
        this.shape = shape;
    }

    public StyledSearchInteractor(SearchInteractor searchInteractor, Color color, NodeShape nodeShape) {
        super(searchInteractor.getInteractorAc(),
                searchInteractor.getInteractorName(),
                searchInteractor.getInteractorPreferredIdentifier(),
                searchInteractor.getInteractorDescription(),
                searchInteractor.getInteractorAlias(),
                searchInteractor.getInteractorAltIds(),
                searchInteractor.getInteractorType(),
                searchInteractor.getInteractorSpecies(),
                searchInteractor.getInteractorTaxId(),
                searchInteractor.getInteractorXrefs(),
                searchInteractor.getInteractionCount(),
                searchInteractor.getInteractionSearchCount(),
                searchInteractor.getInteractionIds(),
                searchInteractor.getInteractorFeatureShortLabels(),
                searchInteractor.getInteractorAliasNames(),
                searchInteractor.getInteractorIntactName(),
                searchInteractor.getInteractionXrefs(),
                searchInteractor.getInteractorFeatureTypes(),
                searchInteractor.getInteractorTypeMIIdentifier());
        this.color = color;
        this.shape = nodeShape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public NodeShape getShape() {
        return shape;
    }

    public void setShape(NodeShape shape) {
        this.shape = shape;
    }
}
