package company.wallet.exception;

import java.util.Map;

class WalletNotActiveException extends WalletException {
    public WalletNotActiveException(String message, Map<String, Object> diagnostics) {
        super(message, 403, diagnostics);
    }
}
