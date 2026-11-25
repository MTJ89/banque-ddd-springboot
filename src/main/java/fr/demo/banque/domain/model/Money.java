package fr.demo.banque.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Value Object : un montant en euros, toujours arrondi à deux décimales. */
public record Money(BigDecimal amount) {
    public Money {
        Objects.requireNonNull(amount, "Le montant est obligatoire");
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money euros(String amount) {
        return new Money(new BigDecimal(amount));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public Money add(Money other) {
        return new Money(amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(amount.subtract(other.amount));
    }

    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money other) {
        return amount.compareTo(other.amount) > 0;
    }
}
