package bank.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tranId;
    @JoinColumn(columnDefinition = "accountNumber")
    @OneToOne(cascade = CascadeType.ALL)
    private Account accNumber;
    @Column
    private Date transactionDate;
    @Column
    private String description;
    @Column
    private int deposit;
    @Column
    private int withdraw;
    @Column
    private int balance;

    public int getTranId() {
        return tranId;
    }


    public Account getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(Account accNumber) {
        this.accNumber = accNumber;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(int withdraw) {
        this.withdraw = withdraw;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "tranId=" + tranId +
                ", accNumber=" + accNumber +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                ", deposit=" + deposit +
                ", withdraw=" + withdraw +
                ", balance=" + balance +
                '}';
    }
}
