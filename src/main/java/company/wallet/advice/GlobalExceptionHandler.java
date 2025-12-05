package company.wallet.advice;

import company.wallet.api.ApiResponse;
import company.wallet.api.dto.ErrorResponse;
import company.wallet.exception.WalletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<ApiResponse<Void>> handleWalletException(WalletException ex) {
        log.error("WalletException occurred: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.valueOf(ex.getHttpStatus());
        ErrorResponse errorResponse = new ErrorResponse(ex.getHttpStatus(), ex.getDiagnostics());
        return ResponseEntity.status(status).body(ApiResponse.error(errorResponse));
    }

    //todo улучшить
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(new ErrorResponse(403, Map.of("errorReason", "notValidRequest"))));
    }


}
