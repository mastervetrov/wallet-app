package company.wallet.service.deposit;

import java.math.BigDecimal;
import java.util.UUID;

public interface DepositService {

    void deposit(UUID walletId, UUID userId, BigDecimal amount);
}
