package company.wallet.controller;

import company.wallet.api.ApiResponse;
import company.wallet.common.security.JwtUserDetailsImpl;
import company.wallet.request.WalletOperationRequest;
import company.wallet.response.WalletBalanceResponse;
import company.wallet.service.WalletOperationDispatcherImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public void request(@Valid @RequestBody WalletOperationRequest request,
                        @AuthenticationPrincipal JwtUserDetailsImpl userDetails) {

        log.debug("Request received. Type: {}, amount: {}, wallet_Id: {}, requester: {}", request.getOperationType(), request.getAmount(),
                request.getValletId(), userDetails.getId());

        walletDispatcher.processOperation(request, userDetails.getId());
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<WalletBalanceResponse> getByWalletId(
            @PathVariable(name = "WALLET_UUID") UUID walletId,
            @AuthenticationPrincipal JwtUserDetailsImpl userDetails) {

        log.debug("Balance requested. walletId: {}, requester: {}", walletId, userDetails.getId());
        WalletBalanceResponse balance = walletDispatcher.getBalanceById(walletId, userDetails.getId());
        log.debug("Successful balance return. Wallet_Id: {}, requester: {}", walletId, userDetails.getId());

        return ApiResponse.success(balance);
    }

}