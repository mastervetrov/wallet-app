package company.wallet.service.balance;

import company.wallet.exception.WalletException;
import company.wallet.model.Wallet;
import company.wallet.repository.WalletRepository;
import company.wallet.response.WalletBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final WalletRepository walletRepository;

    @Override
    public WalletBalanceResponse getBalanceById(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() ->
                WalletException.walletNotFound(walletId));

        return toWalletBalanceResponse(wallet);
    }

    private WalletBalanceResponse toWalletBalanceResponse(Wallet wallet) {
        WalletBalanceResponse response = new WalletBalanceResponse();
        response.setWalletId(wallet.getId());
        response.setUserId(wallet.getUserId());
        response.setBalance(wallet.getBalance());
        response.setAvailableBalance(wallet.getAvailableBalance());
        response.setCurrency(wallet.getCurrency().name());
        response.setLastUpdated(wallet.getUpdatedAt());
        return response;
    }
}
