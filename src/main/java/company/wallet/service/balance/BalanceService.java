package company.wallet.service.balance;

import company.wallet.response.WalletBalanceResponse;

import java.util.UUID;

public interface BalanceService {

    WalletBalanceResponse getBalanceById(UUID walletId);

}
