package bank.service;

import bank.entity.Account;

public interface IAccountService {
    Account getAccountByAccountNumber(String accountNumber);

    int getAccountBalance(String accountNumber);

    void createAccount(Account account);
}
