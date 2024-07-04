package bank.exceptions;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException() {
        super("Invalid Date format");
    }
}
