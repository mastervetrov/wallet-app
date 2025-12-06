package company.wallet.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
public class WalletBalanceResponse {

    private UUID walletId;

    private BigDecimal balance;

    private String currency;

    private BigDecimal availableBalance;

    private Instant lastUpdated;
}
