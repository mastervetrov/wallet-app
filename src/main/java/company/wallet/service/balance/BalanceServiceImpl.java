package company.wallet.service.balance;

import company.wallet.exception.WalletException;
import company.wallet.model.Wallet;
import company.wallet.repository.WalletRepository;
import company.wallet.response.WalletBalanceResponse;
import company.wallet.service.diagnostic.DiagnosticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final WalletRepository walletRepository;
    private final DiagnosticService diagnosticService;

    @Override
    public WalletBalanceResponse getBalanceById(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() ->
                WalletException.walletNotFound(walletId));

        return toWalletBalanceResponse(wallet);
    }

    private WalletBalanceResponse toWalletBalanceResponse(Wallet wallet) {
        WalletBalanceResponse response = new WalletBalanceResponse();
        response.setBalance(wallet.getBalance());
        response.setWalletId(wallet.getId());
        response.setAvailableBalance(wallet.getAvailableBalance());
        response.setCurrency(wallet.getCurrency());
        response.setLastUpdated(wallet.getUpdatedAt());
        return response;
    }
}
