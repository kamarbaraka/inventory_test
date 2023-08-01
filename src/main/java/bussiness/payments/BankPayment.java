package bussiness.payments;

import persistence.Payment;

import java.math.BigDecimal;

/**
 *  a class to simulate a bank payment.
 *  @author kamar baraka.*/

public class BankPayment extends Payment {

    public BankPayment(String bankName, String accountName, BigDecimal amount) {
        super();
        super.setBankName(bankName);
        super.setAccountName(accountName);
        super.setAmount(amount);
    }
}
