package company.wallet.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class WalletBalanceResponse {

    private UUID walletId;

    private BigDecimal balance;

    private String currency;

    private BigDecimal availableBalance;

    private Instant lastUpdated;
}
