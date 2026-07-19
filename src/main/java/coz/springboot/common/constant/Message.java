package coz.springboot.common.constant;

public class Message {

    public static class Exception {

        public static final String BAD_REQUEST = "message.exception.BAD_REQUEST";

        public static final String NOT_FOUND = "message.exception.NOT_FOUND";

        public static final String INTERNAL_SERVER_ERROR = "message.exception.INTERNAL_SERVER_ERROR";

        public static final String SAVE_FAILURE = "message.exception.SAVE_FAILURE";

        public static final String SAVE_EXIST_FAILURE = "message.exception.SAVE_EXIST_FAILURE";

        public static final String UPDATE_FAILURE = "message.exception.UPDATE_FAILURE";

        public static final String DELETE_FAILURE = "message.exception.DELETE_FAILURE";
    }

    public static class Validation {

        public static final String FIELD_IS_REQUIRED = "message.validation.FIELD_IS_REQUIRED";

        public static final String REQUIRED_MISSING = "message.validation.REQUIRED_MISSING";

        public static final String INVALID_SIZE = "message.validation.INVALID_SIZE";

        public static final String MIN_SIZE_INVALID = "message.validation.MIN_SIZE_INVALID";

        public static final String MAX_SIZE_INVALID = "message.validation.MAX_SIZE_INVALID";

        public static final String RANGE_SIZE_INVALID = "message.validation.RANGE_SIZE_INVALID";
    }

    public static class Code {

        public static final String GROUP_CODE_DELETE_FAILURE_BY_ENABLED_COMMON_CODE =
                "message.code.GROUP_CODE_DELETE_FAILURE_BY_ENABLED_COMMON_CODE";
    }
}
