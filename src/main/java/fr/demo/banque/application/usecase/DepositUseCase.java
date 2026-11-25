package fr.demo.banque.application.usecase;

import fr.demo.banque.domain.exception.AccountNotFoundException;
import fr.demo.banque.domain.model.Account;
import fr.demo.banque.domain.model.Money;
import fr.demo.banque.domain.port.AccountRepository;

import java.util.UUID;

public class DepositUseCase {
    private final AccountRepository accounts;

    public DepositUseCase(AccountRepository accounts) {
        this.accounts = accounts;
    }

    public Money execute(UUID accountId, Money amount) {
        Account account = accounts.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        account.deposit(amount);
        accounts.save(account);
        return account.balance();
    }
}
