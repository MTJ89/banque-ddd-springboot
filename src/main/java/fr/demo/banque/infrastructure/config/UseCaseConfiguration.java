package fr.demo.banque.infrastructure.config;

import fr.demo.banque.application.usecase.DepositUseCase;
import fr.demo.banque.application.usecase.GetBalanceUseCase;
import fr.demo.banque.application.usecase.WithdrawUseCase;
import fr.demo.banque.domain.port.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {
    @Bean
    DepositUseCase depositUseCase(AccountRepository repository) {
        return new DepositUseCase(repository);
    }

    @Bean
    WithdrawUseCase withdrawUseCase(AccountRepository repository) {
        return new WithdrawUseCase(repository);
    }

    @Bean
    GetBalanceUseCase getBalanceUseCase(AccountRepository repository) {
        return new GetBalanceUseCase(repository);
    }
}
