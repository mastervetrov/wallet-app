package company.wallet.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponse {
    private final int errorCode;
    private final Map<String, Object> diagnostics;
}
