package company.wallet.exception;

import java.util.Map;

class WithdrawalFailedException extends WalletException {
    public WithdrawalFailedException(String message, Map<String, Object> diagnostics) {
        super(message, 403, diagnostics);
    }
}