package coz.springboot.common.dto;

import java.util.List;

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

    @Schema(description = "Failure")
    private Failure failure;

    @Getter
    @Builder
    public static class Failure {

        @Schema(description = "Failure message key")
        private String failureMessageKey;

        @Schema(description = "Failure label keys")
        private List<String> failureLabelKeys;

        public static Failure of(String failureMessageKey, String... failureLabelKeys) {
            return Failure.builder()
                    .failureMessageKey(failureMessageKey)
                    .failureLabelKeys(List.of(failureLabelKeys))
                    .build();
        }
    }

    public static <T> RestResponse<T> ofSuccess(T data) {
        return RestResponse.<T>builder().succeed(true).data(data).build();
    }

    public static <T> RestResponse<T> ofFailure(String failureMessageKey, String... failureLabelKeys) {
        Failure failure = Failure.builder()
                .failureMessageKey(failureMessageKey)
                .failureLabelKeys(failureLabelKeys == null ? List.of() : List.of(failureLabelKeys))
                .build();
        return RestResponse.<T>builder().succeed(false).failure(failure).build();
    }

    public static <T> RestResponse<T> ofFailure(Failure failure) {
        return RestResponse.<T>builder().succeed(false).failure(failure).build();
    }
}
