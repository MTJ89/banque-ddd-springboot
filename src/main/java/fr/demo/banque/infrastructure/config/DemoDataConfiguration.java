package fr.demo.banque.infrastructure.config;

import fr.demo.banque.domain.model.Account;
import fr.demo.banque.domain.model.Money;
import fr.demo.banque.domain.port.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class DemoDataConfiguration {
    // Compte de démonstration : 11111111-1111-1111-1111-111111111111
    public static final UUID DEMO_ACCOUNT_ID =
            UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Bean
    CommandLineRunner seedAccount(AccountRepository repository) {
        return args -> repository.save(new Account(DEMO_ACCOUNT_ID, Money.euros("1000.00")));
    }
}
