package coz.springboot.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {

    @Schema(description = "Succeed or Failure", example = "true")
    private boolean succeed;

    @Schema(description = "Core Data")
    private T data;

    @Schema(description = "Message for failure")
    private String failureMessage;

    public static <T> RestResponse<T> ofSuccess(T data) {
        return RestResponse.<T>builder().succeed(true).data(data).build();
    }

    public static RestResponse<String> ofFailure(String message) {
        return RestResponse.<String>builder()
                .succeed(false)
                .failureMessage(message)
                .build();
    }
}
