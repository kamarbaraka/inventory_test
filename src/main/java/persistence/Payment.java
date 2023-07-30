package persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

/**
 *  an class to describe a payment.
 *  @author kamar baraka.*/

@Entity
public abstract class Payment {

    @Id
    @GeneratedValue
    private long id;

    private String bankName;

    private String accountName;

    private String mobileNumber;

    private BigDecimal amount;

    public String getBankName() {
        return bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
