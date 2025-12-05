package company.wallet.api;

import company.wallet.api.dto.ErrorResponse;
import company.wallet.api.dto.Metadata;

/**
 * Universal response API
 *
 * @param <T> data
 */
public record ApiResponse<T>(
        boolean success,
        T data,
        ErrorResponse error,
        Metadata metadata
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                data,
                null,
                Metadata.create()
        );
    }

    public static <T> ApiResponse<T> error(ErrorResponse error) {
        return new ApiResponse<>(
                false,
                null,
                error,
                Metadata.create()
        );
    }

}
