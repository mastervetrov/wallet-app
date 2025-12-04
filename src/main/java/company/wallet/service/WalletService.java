package company.wallet.service;

import company.wallet.request.WalletOperationRequest;
import company.wallet.response.WalletBalanceResponse;

import java.util.UUID;

public interface WalletService {

    WalletBalanceResponse getBalanceById(UUID walletId);

    void executeOperation(WalletOperationRequest request);
}
