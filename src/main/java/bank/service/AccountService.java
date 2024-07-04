package bank.service;

import bank.entity.Account;
import bank.exceptions.AccountNotFoundException;
import bank.repository.AccountRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    @Autowired
    AccountRepo accountRepo;

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        Optional<Account> accountById = accountRepo.findById(accountNumber);
        if(accountById.isPresent()) {
            return accountById.get();
        }
        throw new AccountNotFoundException();
    }

    @Override
    public void createAccount(Account account) {
        accountRepo.save(account);
        LOGGER.info("Account created");
    }

    @Override
    public int getAccountBalance(String accountNumber) {
        Account accountByAccountNumber = getAccountByAccountNumber(accountNumber);
        return accountByAccountNumber.getBalanceAmount();
    }

}
