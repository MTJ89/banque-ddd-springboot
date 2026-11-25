package fr.demo.banque.infrastructure.web;

import fr.demo.banque.domain.exception.AccountNotFoundException;
import fr.demo.banque.domain.exception.InsufficientBalanceException;
import fr.demo.banque.domain.exception.InvalidAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, String> notFound(RuntimeException ex) {
        return Map.of("erreur", ex.getMessage());
    }

    @ExceptionHandler({InsufficientBalanceException.class, InvalidAmountException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> businessError(RuntimeException ex) {
        return Map.of("erreur", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> validationError(MethodArgumentNotValidException ex) {
        return Map.of("erreur", "Le montant doit être supérieur à zéro et comporter au maximum deux décimales");
    }
}
