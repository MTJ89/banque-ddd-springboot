package fr.demo.banque.infrastructure.web;

import fr.demo.banque.application.usecase.DepositUseCase;
import fr.demo.banque.application.usecase.GetBalanceUseCase;
import fr.demo.banque.application.usecase.WithdrawUseCase;
import fr.demo.banque.domain.model.Money;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/comptes")
public class BankAccountController {
    private final DepositUseCase deposit;
    private final WithdrawUseCase withdraw;
    private final GetBalanceUseCase getBalance;

    public BankAccountController(DepositUseCase deposit,
                                 WithdrawUseCase withdraw,
                                 GetBalanceUseCase getBalance) {
        this.deposit = deposit;
        this.withdraw = withdraw;
        this.getBalance = getBalance;
    }

    @PostMapping("/{id}/versements")
    @ResponseStatus(HttpStatus.OK)
    public BalanceResponse deposit(@PathVariable UUID id, @Valid @RequestBody AmountRequest request) {
        return BalanceResponse.from(deposit.execute(id, new Money(request.montant())));
    }

    @PostMapping("/{id}/retraits")
    public BalanceResponse withdraw(@PathVariable UUID id, @Valid @RequestBody AmountRequest request) {
        return BalanceResponse.from(withdraw.execute(id, new Money(request.montant())));
    }

    @GetMapping("/{id}/solde")
    public BalanceResponse balance(@PathVariable UUID id) {
        return BalanceResponse.from(getBalance.execute(id));
    }

    public record AmountRequest(
            @NotNull @DecimalMin(value = "0.01") @Digits(integer = 12, fraction = 2)
            BigDecimal montant) {}

    public record BalanceResponse(BigDecimal solde, String devise) {
        static BalanceResponse from(Money money) {
            return new BalanceResponse(money.amount(), "EUR");
        }
    }
}
