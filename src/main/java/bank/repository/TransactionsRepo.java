package bank.repository;

import bank.entity.Account;
import bank.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, Integer> {
    List<Transactions> findByAccNumber(Account accountNumber);
    List<Transactions> findByAccNumberOrderByTransactionDateDesc(Account accountNumber);
    List<Transactions> findByAccNumberAndTransactionDateBetween(Account accNumber, Date from, Date to);
}
