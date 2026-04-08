package coz.springboot.common.dto;

import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {

    private boolean succeed;

    private T data;

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
