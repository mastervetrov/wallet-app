package company.wallet.request;

import company.wallet.model.OperationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class WalletOperationRequest {

    @NotNull(message = "ValletId is requered")
    private UUID valletId; //todo так указано в тестовом задании, выглядит больно, но делаем
    @NotNull(message = "OperationType is required")
    private OperationType operationType;
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive ")
    @DecimalMin(value = "0.01", message = "Minimum amount is 0.01")
    @Digits(integer = 12, fraction = 2, message = "Amount must have max 12 digits before and 2 after decimal")
    private BigDecimal amount;
}
