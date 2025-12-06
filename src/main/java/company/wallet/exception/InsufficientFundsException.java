package company.wallet.exception;

import java.util.Map;

class InsufficientFundsException extends WalletException {
    public InsufficientFundsException(String message, Map<String, Object> diagnostics) {
        super(message, 403, diagnostics);
    }
}
