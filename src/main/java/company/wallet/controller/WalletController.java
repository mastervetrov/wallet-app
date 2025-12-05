package company.wallet.controller;

import company.wallet.api.ApiResponse;
import company.wallet.request.WalletOperationRequest;
import company.wallet.response.WalletBalanceResponse;
import company.wallet.service.WalletOperationDispatcherImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class WalletController {

    private final WalletOperationDispatcherImpl walletDispatcher;

    @PostMapping("/wallet")
    @ResponseStatus(HttpStatus.OK)
    public void request(@Valid @RequestBody WalletOperationRequest request) {
        log.debug("Request received. Type: {}, amount: {}, wallet_Id: {}", request.getOperationType(), request.getAmount(),
                request.getValletId());
        walletDispatcher.processOperation(request);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<WalletBalanceResponse> getByWalletId(@PathVariable(name = "WALLET_UUID") UUID walletId) {
        log.debug("Balance requested. walletId: {}", walletId);
        WalletBalanceResponse balance = walletDispatcher.getBalanceById(walletId);
        log.debug("Successful balance return. Wallet_Id: {}", walletId);
        return ApiResponse.success(balance);
    }

}