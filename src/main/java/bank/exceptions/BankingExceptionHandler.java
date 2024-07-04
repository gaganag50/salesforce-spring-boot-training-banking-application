package bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankingExceptionHandler {
    @ExceptionHandler(value = {AccountNotFoundException.class, TransactionNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {InSufficientBalanceException.class, InvalidDateException.class})
    public ResponseEntity<String> accountWithInsufficientBalance(InSufficientBalanceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
