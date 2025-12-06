package company.wallet.common.security.exception;

public class JwtTokenInvalidException extends RuntimeException{
    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
