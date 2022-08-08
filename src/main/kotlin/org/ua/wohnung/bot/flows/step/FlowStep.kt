package org.ua.wohnung.bot.flows.step

enum class FlowStep {
    INITIAL,
    CONVERSATION_START,
    ACCEPT_POLICIES,
    PERSONAL_DATA_PROCESSING_APPROVAL,
    BUNDESLAND_SELECTION,
    DISTRICT_SELECTION,
    FAMILY_COUNT,
    FAMILY_MEMBERS,
    FIRSTNAME_AND_LASTNAME,
    PHONE_NUMBER,
    PETS,
    FOREIGN_LANGUAGES,
    READY_TO_MOVE,
    ALLERGIES,
    REGISTRATION_FINISHED_SUCCESS,

    FORWARD_TO_WEB,
    CONDITIONS,

    NO_USER_NAME_ERROR,
    REGISTRATION_INCOMPLETE_ERROR,

    CONVERSATION_FINISHED_DECLINED,
    REGISTERED_USER_CONVERSATION_START,
    REGISTERED_USER_LIST_APARTMENTS,
    REGISTERED_USER_APPLY_FOR_APARTMENT,

    REGISTERED_USER_REQUEST_RECEIVED,
    REGISTERED_USER_REQUEST_DECLINED,
    CONVERSATION_FINISH_REMOVAL,
    OWNER_START,
    OWNER_APARTMENTS_LOADED,

    OWNER_ADD_ADMIN,
    OWNER_REMOVE_ADMIN,
    OWNER_LIST_ADMINS,
    OWNER_ADD_ADMIN_DONE,
    ADMIN_START,
    ADMIN_WHO_IS_INTERESTED_ASK_APARTMENT_ID,
    ADMIN_LIST_APPLICANTS;
}
