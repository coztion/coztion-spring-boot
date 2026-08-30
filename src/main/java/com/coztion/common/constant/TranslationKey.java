package com.coztion.common.constant;

public class TranslationKey {

    public static class Common {

        public static class Label {
            public static final String SORT_ORDER = "common.label.SORT_ORDER";
            public static final String DATA_STATUS = "common.label.DATA_STATUS";
        }

        public static class Message {

            public static final String CREATE_FAILURE = "common.message.CREATE_FAILURE";
            public static final String UPDATE_FAILURE = "common.message.UPDATE_FAILURE";
            public static final String DELETE_FAILURE = "common.message.DELETE_FAILURE";
            public static final String DUPLICATE_FAILURE = "common.message.DUPLICATE_FAILURE";
            public static final String REQUEST_VALIDATION_FAILURE = "common.message.REQUEST_VALIDATION_FAILURE";
            public static final String REQUIRED_VALIDATION_FAILURE = "common.message.REQUIRED_VALIDATION_FAILURE";
            public static final String MIN_SIZE_VALIDATION_FAILURE = "common.message.MIN_SIZE_VALIDATION_FAILURE";
            public static final String MAX_SIZE_VALIDATION_FAILURE = "common.message.MAX_SIZE_VALIDATION_FAILURE";
            public static final String RANGE_SIZE_VALIDATION_FAILURE = "common.message.RANGE_SIZE_VALIDATION_FAILURE";
            public static final String INTERNAL_SERVER_ERROR_FAILURE = "common.message.INTERNAL_SERVER_ERROR_FAILURE";
            public static final String NOT_FOUND_FAILURE = "common.message.NOT_FOUND_FAILURE";
        }
    }

    public static class Code {

        public static class Label {
            public static final String GROUP_CODE = "code.label.GROUP_CODE";
            public static final String GROUP_CODE_NAME = "code.label.GROUP_CODE_NAME";
            public static final String COMMON_CODE = "code.label.COMMON_CODE";
            public static final String COMMON_CODE_NAME = "code.label.COMMON_CODE_NAME";
        }

        public static class Message {
            public static final String GROUP_CODE_DELETE_FAILURE_BY_ENABLED_COMMON_CODE =
                    "code.message.GROUP_CODE_DELETE_FAILURE_BY_ENABLED_COMMON_CODE";
        }
    }

    public static class User {

        public static class Message {

            public static final String AUTHENTICATION_FAILURE = "user.message.AUTHENTICATION_FAILURE";
            public static final String AUTHORIZATION_FAILURE = "user.message.AUTHORIZATION_FAILURE";
        }
    }

    public static class Menu {

        public static class Label {

            public static final String MENU = "menu.label.MENU";
            public static final String MENU_ID = "menu.label.MENU_ID";
            public static final String MENU_NAME = "menu.label.MENU_NAME";
            public static final String MENU_TYPE = "menu.label.MENU_TYPE";
            public static final String PARENT_MENU_ID = "menu.label.PARENT_MENU_ID";
            public static final String URL = "menu.label.URL";
        }

        public static class Message {
            public static final String MENU_DELETE_FAILURE_BY_SUB_MENU = "menu.message.MENU_DELETE_FAILURE_BY_SUB_MENU";
        }
    }

    public static class Translation {

        public static class Label {

            public static final String TRANSLATION_GROUP = "translation.label.TRANSLATION_GROUP";
            public static final String TRANSLATION_TYPE = "translation.label.TRANSLATION_TYPE";
            public static final String TRANSLATION_CODE = "translation.label.TRANSLATION_CODE";
            public static final String TRANSLATION_LOCALE = "translation.label.TRANSLATION_LOCALE";
            public static final String TRANSLATION_CONTENT = "translation.label.TRANSLATION_CONTENT";
        }
    }
}
