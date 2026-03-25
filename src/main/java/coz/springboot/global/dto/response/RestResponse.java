package coz.springboot.global.dto.response;

import lombok.*;

import coz.springboot.global.enums.RestResponseType;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {

    private RestResponseType type;

    private T data;

    public static <T> RestResponse<T> ofSuccess(T data) {
        return RestResponse.<T>builder()
                .type(RestResponseType.SUCCESS)
                .data(data)
                .build();
    }

    public static RestResponse<String> ofFailure(String message) {
        return RestResponse.<String>builder()
                .type(RestResponseType.FAILURE)
                .data(message)
                .build();
    }
}
