package coz.springboot.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {

    @Schema(description = "Succeed or Failure")
    private boolean succeed;

    @Schema(description = "Response data")
    private T data;

    @Schema(description = "Failure code")
    private String failureCode;

    @Schema(description = "Failure message")
    private String failureMessage;

    public static <T> RestResponse<T> ofSuccess(T data) {
        return RestResponse.<T>builder().succeed(true).data(data).build();
    }

    public static <T> RestResponse<T> ofFailureCode(String failureCode) {
        return RestResponse.<T>builder().succeed(false).failureCode(failureCode).build();
    }

    public static RestResponse<String> ofFailureMessage(String failureMessage) {
        return RestResponse.<String>builder()
                .succeed(false)
                .failureMessage(failureMessage)
                .build();
    }
}
