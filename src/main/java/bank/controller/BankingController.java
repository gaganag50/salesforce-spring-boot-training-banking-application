package bank.controller;

import bank.entity.Account;
import bank.entity.Transactions;
import bank.service.IAccountService;
import bank.service.ITransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BankingController {
    @Autowired
    IAccountService accountService;
    @Autowired
    ITransactionsService transactionsService;

    @PostMapping("/account")
    public ResponseEntity<String> createAccount(@RequestParam String name, @RequestParam int amount) {
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setCustomerName(name);
        account.setBalanceAmount(amount);
        accountService.createAccount(account);
        return ResponseEntity.ok("account saved");
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam String accountNumber, @RequestParam int amount) {
        transactionsService.createTransaction(accountNumber, amount, true);
        return ResponseEntity.ok("deposit success");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam String accountNumber, @RequestParam int amount) {
        transactionsService.createTransaction(accountNumber, amount, false);
        return ResponseEntity.ok("withdraw success");
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Integer> getAccountBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountBalance(accountNumber));
    }

    @GetMapping("/statement/{accountNumber}")
    public ResponseEntity<List<Transactions>> getStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionsService.getAccountStatement(accountNumber, false));
    }

    @GetMapping("/statement/datewise/{accountNumber}")
    public ResponseEntity<List<Transactions>> getStatementInDescendingOrder(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionsService.getAccountStatement(accountNumber, true));
    }

    @GetMapping("/statement/betweenDates/{accountNumber}")
    public ResponseEntity<List<Transactions>> getStatementInBetweenDates(@PathVariable String accountNumber, @RequestParam String startDate, @RequestParam String endDate) {
        return ResponseEntity.ok(transactionsService.getAccountStatementBetweenDates(accountNumber, startDate, endDate));
    }
}
