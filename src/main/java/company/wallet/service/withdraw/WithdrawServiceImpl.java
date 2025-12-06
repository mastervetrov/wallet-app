package company.wallet.service.withdraw;

import company.wallet.repository.WalletRepository;
import company.wallet.service.diagnostic.DiagnosticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawServiceImpl implements WithdrawService{

    private final WalletRepository walletRepository;
    private final DiagnosticService diagnosticService;

    @Override
    public void withdraw(UUID walletId, UUID userId, BigDecimal amount) {
        int processedRows = walletRepository.withdrawAtomic(walletId, amount, BigDecimal.ZERO);
        if (processedRows == 0) {
            diagnosticService.handleFailedWithdraw(walletId, amount);
        }
    }
}
