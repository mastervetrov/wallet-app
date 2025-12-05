package company.wallet.exception;

import java.util.Map;

class MaxBalanceExceededException extends WalletException {
    public MaxBalanceExceededException(String message, Map<String, Object> diagnostics) {
        super(message, 403, diagnostics);
    }
}