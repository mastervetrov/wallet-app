package company.wallet.service.diagnostic;

import company.wallet.exception.WalletException;
import company.wallet.model.Wallet;
import company.wallet.model.WalletStatus;
import company.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosticServiceImpl implements DiagnosticService {

    private final WalletRepository walletRepository;
    private static final BigDecimal MAX_BALANCE = new BigDecimal("1000000.00");

    @Override
    public void handleFailedDeposit(UUID walletId, BigDecimal amount) {
        Wallet wallet = getWallet(walletId);

        if (wallet.getStatus() != WalletStatus.ACTIVE) {
            log.warn("Deposit failed: wallet {} is {}", walletId, wallet.getStatus());
            throw WalletException.walletNotActive(walletId, wallet.getStatus(), "DEPOSIT");
        }

        BigDecimal newBalance = wallet.getBalance().add(amount);
        if (newBalance.compareTo(MAX_BALANCE) > 0) {
            log.warn("Deposit failed: max balance exceeded for wallet {}", walletId);
            throw WalletException.maxBalanceExceeded(
                    walletId, amount, wallet.getBalance(), MAX_BALANCE, newBalance
            );
        }

        log.error("Deposit failed for unknown reason. Wallet: {}, amount: {}", walletId, amount);
        throw WalletException.depositFailed(walletId, amount, wallet.getBalance());
    }

    @Override
    public void handleFailedWithdraw(UUID walletId, BigDecimal amount) {
        Wallet wallet = getWallet(walletId);

        if (wallet.getStatus() != WalletStatus.ACTIVE) {
            log.warn("Withdraw failed: wallet {} is {}", walletId, wallet.getStatus());
            throw WalletException.walletNotActive(walletId, wallet.getStatus(), "WITHDRAW");
        }

        if (wallet.getAvailableBalance().compareTo(amount) < 0) {
            log.warn("Withdraw failed: insufficient funds for wallet {}", walletId);
            throw WalletException.insufficientFunds(
                    walletId, amount, wallet.getAvailableBalance(), wallet.getBalance()
            );
        }

        log.error("Withdraw failed for unknown reason. Wallet: {}, amount: {}", walletId, amount);
        throw WalletException.withdrawalFailed(walletId, amount, wallet.getBalance());
    }

    private Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> WalletException.walletNotFound(walletId));
    }
}
