package company.wallet.exception;

import company.wallet.model.WalletStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
public class WalletException extends RuntimeException {

    private final String errorCode;
    private final Map<String, Object> diagnostics;

    protected WalletException(String message, String errorCode, Map<String, Object> diagnostics) {
        super(message);
        this.errorCode = errorCode;
        this.diagnostics = diagnostics;
    }

    public static WalletException walletNotFound(UUID walletId) {
        return new WalletException(
                "Wallet not found: " + walletId,
                "WALLET_NOT_FOUND",
                Map.of(
                        "walletId", walletId,
                        "timestamp", Instant.now()
                )
        );
    }

    public static WalletException walletNotActive(UUID walletId, WalletStatus currentStatus, String operation) {
        return new WalletException(
                String.format("Wallet is %s. Cannot process %s",
                        currentStatus.name().toLowerCase(), operation.toLowerCase()),
                "WALLET_NOT_ACTIVE",
                Map.of(
                        "walletId", walletId,
                        "operation", operation,
                        "currentStatus", currentStatus.name(),
                        "requiredStatus", WalletStatus.ACTIVE.name(),
                        "timestamp", Instant.now()
                )
        );
    }

    public static WalletException maxBalanceExceeded(UUID walletId, BigDecimal amount,
                                                     BigDecimal currentBalance,
                                                     BigDecimal maxBalance,
                                                     BigDecimal newBalance) {
        return new WalletException(
                String.format("Deposit would exceed maximum balance by %s",
                        newBalance.subtract(maxBalance)),
                "MAX_BALANCE_EXCEEDED",
                Map.of(
                        "walletId", walletId,
                        "operation", "DEPOSIT",
                        "amount", amount,
                        "currentBalance", currentBalance,
                        "newBalance", newBalance,
                        "maxBalance", maxBalance,
                        "exceededBy", newBalance.subtract(maxBalance),
                        "timestamp", Instant.now()
                )
        );
    }

    public static WalletException insufficientFunds(UUID walletId, BigDecimal requestedAmount,
                                                    BigDecimal availableBalance,
                                                    BigDecimal currentBalance) {
        return new WalletException(
                String.format("Insufficient available funds. Available: %s, Requested: %s",
                        availableBalance, requestedAmount),
                "INSUFFICIENT_FUNDS",
                Map.of(
                        "walletId", walletId,
                        "operation", "WITHDRAW",
                        "requestedAmount", requestedAmount,
                        "availableBalance", availableBalance,
                        "currentBalance", currentBalance,
                        "shortage", requestedAmount.subtract(availableBalance),
                        "timestamp", Instant.now()
                )
        );
    }

    public static WalletException depositFailed(UUID walletId, BigDecimal amount, BigDecimal currentBalance) {
        return new WalletException(
                "Deposit operation failed",
                "DEPOSIT_FAILED",
                Map.of(
                        "walletId", walletId,
                        "operation", "DEPOSIT",
                        "amount", amount,
                        "currentBalance", currentBalance,
                        "timestamp", Instant.now()
                )
        );
    }

    public static WalletException withdrawalFailed(UUID walletId, BigDecimal amount, BigDecimal currentBalance) {
        return new WalletException(
                "Withdrawal operation failed",
                "WITHDRAWAL_FAILED",
                Map.of(
                        "walletId", walletId,
                        "operation", "WITHDRAW",
                        "amount", amount,
                        "currentBalance", currentBalance,
                        "timestamp", Instant.now()
                )
        );
    }
}