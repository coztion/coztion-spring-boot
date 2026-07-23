package coz.springboot.common.constant;

public class Message {

    public static class Common {

        public static class Label {
            public static final String SORT_ORDER = "common.label.SORT_ORDER";
            public static final String DATA_STATUS = "common.label.DATA_STATUS";
        }

        public static class Failure {
            public static final String INTERNAL_SERVER_ERROR_FAILURE = "common.failure.INTERNAL_SERVER_ERROR_FAILURE";
            public static final String NOT_FOUND_FAILURE = "common.failure.NOT_FOUND_FAILURE";
            public static final String SAVE_FAILURE = "common.failure.SAVE_FAILURE";
            public static final String UPDATE_FAILURE = "common.failure.UDDATE_FAILURE";
            public static final String DELETE_FAILURE = "common.failure.DELETE_FAILURE";
            public static final String DUPLICATE_FAILURE = "common.failure.DUPLICATE_FAILURE";

            public static final String REQUEST_VALIDATION_FAILURE = "common.failure.REQUEST_VALIDATION_FAILURE";
            public static final String REQUIRED_VALIDATION_FAILURE = "common.failure.REQUIRED_VALIDATION_FAILURE";
            public static final String MIN_SIZE_VALIDATION_FAILURE = "common.failure.MIN_SIZE_VALIDATION_FAILURE";
            public static final String MAX_SIZE_VALIDATION_FAILURE = "common.failure.MAX_SIZE_VALIDATION_FAILURE";
            public static final String RANGE_SIZE_VALIDATION_FAILURE = "common.failure.RANGE_SIZE_VALIDATION_FAILURE";
        }
    }

    public static class Code {

        public static class Label {
            public static final String GROUP_CODE = "code.label.GROUP_CODE";
            public static final String GROUP_CODE_NAME = "code.label.GROUP_CODE_NAME";
        }

        public static class Failure {
            public static final String GROUP_CODE_DELETE_FAILURE_BY_ENABLED_COMMON_CODE =
                    "code.failure.GROUP_CODE_DELETE_FAILURE_BY_ENABLED_COMMON_CODE";
        }
    }

    public static class Menu {

        public static class Label {
            public static final String MENU_NAME = "menu.label.MENU_NAME";
        }
    }
}
