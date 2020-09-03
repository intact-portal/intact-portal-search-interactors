package uk.ac.ebi.intact.search.interactors.model;

public class SearchInteractorFields {

    public static final String INTERACTOR_AC = "interactor_ac"; //type string

    //copy field of identifiers, *_identifier dynamic type
    public static final String INTERACTOR_IDENTIFIERS = "interactor_identifiers";

    public static final String INTERACTOR_NAME = "interactor_name";
    public static final String INTERACTOR_NAME_STR = "interactor_name_str";

    public static final String INTERACTOR_INTACT_NAME = "interactor_intact_name";

    public static final String INTERACTOR_DESCRIPTION = "interactor_description";

    public static final String INTERACTOR_PREFERRED_ID = "interactor_preferred_id";

    public static final String INTERACTOR_ALIAS = "interactor_alias";
    public static final String INTERACTOR_ALIAS_NAMES = "interactor_alias_names";
    public static final String INTERACTOR_ALIAS_NAMES_STR = "interactor_alias_names_str";

    public static final String INTERACTOR_ALT_IDS = "interactor_alt_ids";

    public static final String INTERACTOR_TAX_ID = "interactor_tax_id";

    public static final String INTERACTOR_XREFS = "interactor_xrefs";

    public static final String INTERACTOR_TYPE = "interactor_type";
    public static final String INTERACTOR_TYPE_STR = "interactor_type_str";

    public static final String INTERACTOR_SPECIES_NAME = "interactor_species_name";
    public static final String INTERACTOR_SPECIES_NAME_STR = "interactor_species_name_str";

    public static final String INTERACTOR_FEATURE_SHORTLABELS = "interactor_feature_shortlabels";

    public static final String INTERACTION_COUNT = "interaction_count";
    public static final String INTERACTION_IDS = "interaction_ids";
    public static final String INTERACTION_XREFS = "interaction_xrefs";

    public static final String DEFAULT = "default"; //Copy field for general search
    public static final String SUGGEST = "suggest"; //Copy field for exact suggestion
    public static final String INTERACTOR_IDENTIFIER_DEFAULT = "interactor_identifiers_default";
    public static final String INTERACTOR_NAMES_DEFAULT = "interactor_names_default";
    public static final String INTERACTION_XREFS_DEFAULT = "interaction_xrefs_default";
    public static final String INTERACTOR_ALIAS_DEFAULT = "interactor_alias_default";
    public static final String INTERACTOR_FEATURE_TYPES = "interactor_feature_types";
}
