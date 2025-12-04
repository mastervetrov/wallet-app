package company.wallet.exception;

import java.util.Map;

class DepositFailedException extends WalletException {
    public DepositFailedException(String message, Map<String, Object> diagnostics) {
        super(message, "DEPOSIT_FAILED", diagnostics);
    }
}