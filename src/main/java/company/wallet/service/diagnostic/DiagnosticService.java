package company.wallet.service.diagnostic;

import java.math.BigDecimal;
import java.util.UUID;

public interface DiagnosticService {
    void handleFailedWithdraw(UUID walletId, BigDecimal amount);

    void handleFailedDeposit(UUID walletId, BigDecimal amount);
}
