package fr.demo.banque.domain.port;

import fr.demo.banque.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Port sortant : le domaine ne dépend pas de la technologie de persistance. */
public interface AccountRepository {
    Optional<Account> findById(UUID id);
    Account save(Account account);
    List<Account> findByBalanceGreaterThan0();
}
