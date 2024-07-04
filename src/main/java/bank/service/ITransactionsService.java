package bank.service;

import bank.entity.Transactions;

import java.util.List;


public interface ITransactionsService {
    void createTransaction(String accountNumber, int amount, boolean isDeposit);

    List<Transactions> getAccountStatement(String accountNumber, boolean isSortedDesc);

    List<Transactions> getAccountStatementBetweenDates(String accountNumber, String startDate, String endDate);
}
