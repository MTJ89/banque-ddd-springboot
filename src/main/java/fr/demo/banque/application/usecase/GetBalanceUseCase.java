package fr.demo.banque.application.usecase;

import fr.demo.banque.domain.exception.AccountNotFoundException;
import fr.demo.banque.domain.model.Money;
import fr.demo.banque.domain.port.AccountRepository;

import java.util.UUID;

public class GetBalanceUseCase {
    private final AccountRepository accounts;

    public GetBalanceUseCase(AccountRepository accounts) {
        this.accounts = accounts;
    }

    public Money execute(UUID accountId) {
        return accounts.findById(accountId)
                .map(account -> account.balance())
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}
