package bussiness.payments;

import persistence.Payment;

import java.math.BigDecimal;

/**
 *  a class to simulate a cash payment.
 *  @author kamar baraka.*/

public class CashPayment extends Payment {

    public CashPayment(BigDecimal amount) {

        super();
        super.setAmount(amount);
    }
}
