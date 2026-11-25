package fr.demo.banque.infrastructure.persistence;

import fr.demo.banque.domain.model.Account;
import fr.demo.banque.domain.port.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAccountRepository implements AccountRepository {
    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Optional<Account> findById(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public Account save(Account account) {
        accounts.put(account.id(), account);
        return account;
    }

    @Override
    public List<Account> findByBalanceGreaterThan0() {
        return accounts.values().stream()
                .filter(account -> account.balance().isPositive())
                .toList();
    }
}
