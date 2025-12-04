package company.wallet.service;

import company.wallet.request.WalletOperationRequest;
import company.wallet.response.WalletBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    @Override
    public WalletBalanceResponse getBalanceById(UUID walletId) {
        return null;
    }

    @Override
    public void executeOperation(WalletOperationRequest request) {

    }
}
