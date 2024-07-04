package bank.service;

import bank.entity.Account;
import bank.entity.Transactions;
import bank.exceptions.InSufficientBalanceException;
import bank.exceptions.InvalidDateException;
import bank.exceptions.TransactionNotFoundException;
import bank.repository.TransactionsRepo;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransactionsService implements ITransactionsService {
    private static final Logger LOGGER = LogManager.getLogger(TransactionsService.class);
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionsRepo transactionsRepo;

    @Transactional
    @Override
    public void createTransaction(String accountNumber, int amount, boolean isDeposit) {
        Transactions transaction = new Transactions();
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        transaction.setAccNumber(account);
        int existingBalance = account.getBalanceAmount();
        if (isDeposit) {
            transaction.setDeposit(amount);
        }
        else {
            if(existingBalance >= amount)
                transaction.setWithdraw(amount);
            else throw new InSufficientBalanceException(accountNumber);
        }
        transaction.setTransactionDate(new Date());
        transaction.setDescription("deposit transaction");
        int updatedBalance = isDeposit ? (existingBalance + amount) : (existingBalance - amount);
        transaction.setBalance(updatedBalance);
        transactionsRepo.save(transaction);
        LOGGER.info("Transaction created");
        account.setBalanceAmount(updatedBalance);
        accountService.createAccount(account);
        LOGGER.info("Account created");
    }

    @Override
    public List<Transactions> getAccountStatement(String accountNumber, boolean isSortedDesc) {
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        List<Transactions> transactionsByAccNumber;
        if (!isSortedDesc) {
            transactionsByAccNumber = transactionsRepo.findByAccNumber(account);
        } else {
            transactionsByAccNumber = transactionsRepo.findByAccNumberOrderByTransactionDateDesc(account);
        }
        if (transactionsByAccNumber.isEmpty()) {
            throw new TransactionNotFoundException();
        }
        return transactionsByAccNumber;
    }

    @Override
    public List<Transactions> getAccountStatementBetweenDates(String accountNumber, String startDateStr, String endDateStr) {
        Account account = accountService.getAccountByAccountNumber(accountNumber);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate;
        Date endDate;
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = DateUtils.addHours(dateFormat.parse(endDateStr), 24);
        } catch (Exception e) {
            throw new InvalidDateException();
        }
        return transactionsRepo.findByAccNumberAndTransactionDateBetween(account, startDate, endDate);
    }
}
