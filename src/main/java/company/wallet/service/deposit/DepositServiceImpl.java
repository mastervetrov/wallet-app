package company.wallet.service.deposit;

import company.wallet.repository.WalletRepository;
import company.wallet.service.diagnostic.DiagnosticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService{

    private final WalletRepository walletRepository;
    private final DiagnosticService diagnosticService;

    @Override
    public void deposit(UUID walletId, BigDecimal amount) {
        int processedRows = walletRepository.depositAtomic(walletId, amount, new BigDecimal("9999999999999.9999"));
        if (processedRows == 0) diagnosticService.handleFailedDeposit(walletId, amount);
    }
}
