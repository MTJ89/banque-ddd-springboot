package fr.demo.banque.domain.model;

import fr.demo.banque.domain.exception.InsufficientBalanceException;
import fr.demo.banque.domain.exception.InvalidAmountException;

import java.util.Objects;
import java.util.UUID;

/** Aggregate Root : protège les invariants métier du compte. */
public class Account {
    private final UUID id;
    private Money balance;

    public Account(UUID id, Money initialBalance) {
        this.id = Objects.requireNonNull(id);
        this.balance = Objects.requireNonNull(initialBalance);
        if (initialBalance.amount().signum() < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif");
        }
    }

    public synchronized void deposit(Money amount) {
        validatePositive(amount);
        balance = balance.add(amount);
    }

    public synchronized void withdraw(Money amount) {
        validatePositive(amount);
        if (amount.isGreaterThan(balance)) {
            throw new InsufficientBalanceException("Solde insuffisant");
        }
        balance = balance.subtract(amount);
    }

    public UUID id() {
        return id;
    }

    public synchronized Money balance() {
        return balance;
    }

    private void validatePositive(Money amount) {
        if (amount == null || !amount.isPositive()) {
            throw new InvalidAmountException("Le montant doit être strictement positif");
        }
    }
}
