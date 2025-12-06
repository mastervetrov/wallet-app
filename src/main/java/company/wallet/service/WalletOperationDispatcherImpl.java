package company.wallet.service;

import company.wallet.request.WalletOperationRequest;
import company.wallet.response.WalletBalanceResponse;
import company.wallet.service.balance.BalanceService;
import company.wallet.service.deposit.DepositService;
import company.wallet.service.withdraw.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletOperationDispatcherImpl implements WalletOperationDispatcher{

    private final WithdrawService withdrawService;
    private final DepositService depositService;
    private final BalanceService balanceService;

    @Override
    public void processOperation(WalletOperationRequest request) {
        switch (request.getOperationType()) {
            case DEPOSIT -> depositService.deposit(request.getValletId(), request.getAmount());
            case WITHDRAW -> withdrawService.withdraw(request.getValletId(), request.getAmount());
        }
    }

    @Override
    public WalletBalanceResponse getBalanceById(UUID walletId) {
        return balanceService.getBalanceById(walletId);
    }
}
