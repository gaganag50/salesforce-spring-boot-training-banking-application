package bank.exceptions;

public class InSufficientBalanceException extends RuntimeException{
    public InSufficientBalanceException(String accountNumber) {
        super("Insufficient balance for account: " + accountNumber);
    }
}
