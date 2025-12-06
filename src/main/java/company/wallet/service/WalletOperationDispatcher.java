package company.wallet.service;

import company.wallet.request.WalletOperationRequest;
import company.wallet.response.WalletBalanceResponse;

import java.util.UUID;

public interface WalletOperationDispatcher {

    void processOperation(WalletOperationRequest request, UUID userId);

    WalletBalanceResponse getBalanceById(UUID walletId, UUID userId);

}
