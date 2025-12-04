package company.wallet.request;

import company.wallet.model.OperationType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class WalletOperationRequest {
    private UUID walletId;
    private OperationType operationType;
    private BigDecimal amount;
    private String currency;
}
