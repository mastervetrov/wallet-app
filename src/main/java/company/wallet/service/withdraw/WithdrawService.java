package company.wallet.service.withdraw;

import java.math.BigDecimal;
import java.util.UUID;

public interface WithdrawService {

    void withdraw(UUID walletId, BigDecimal amount);

}
