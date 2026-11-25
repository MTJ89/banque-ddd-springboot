package fr.demo.banque.domain.model;

import fr.demo.banque.domain.exception.InsufficientBalanceException;
import fr.demo.banque.domain.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void depositIncreasesBalance() {
        Account account = new Account(UUID.randomUUID(), Money.euros("100.00"));
        account.deposit(Money.euros("50.25"));
        assertEquals(Money.euros("150.25"), account.balance());
    }

    @Test
    void withdrawDecreasesBalance() {
        Account account = new Account(UUID.randomUUID(), Money.euros("100.00"));
        account.withdraw(Money.euros("30.00"));
        assertEquals(Money.euros("70.00"), account.balance());
    }

    @Test
    void withdrawMoreThanBalanceIsRejected() {
        Account account = new Account(UUID.randomUUID(), Money.euros("20.00"));
        assertThrows(InsufficientBalanceException.class,
                () -> account.withdraw(Money.euros("21.00")));
    }

    @Test
    void zeroOrNegativeAmountIsRejected() {
        Account account = new Account(UUID.randomUUID(), Money.euros("20.00"));
        assertThrows(InvalidAmountException.class, () -> account.deposit(Money.euros("0.00")));
        assertThrows(InvalidAmountException.class, () -> account.withdraw(Money.euros("-1.00")));
    }
}
