package bussiness.payments;

import persistence.Payment;

import java.math.BigDecimal;

public class MobilePayment extends Payment {

    public MobilePayment(String mobileNumber, double amount) {

        super();
        super.setMobileNumber(mobileNumber);
        super.setAmount(BigDecimal.valueOf(amount));
    }
}
