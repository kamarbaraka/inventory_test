package bussiness.services.operational;

import persistence.Payment;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *  a class to simulate payment processing.
 *  @author kamar baraka.*/

public class PaymentProcessingService {

    private final Scanner input = new Scanner(System.in);

    /**
     *  process bank payment.
     * @param amount the amount to process.
     *  @return the bank payment object*/
    public Payment bankPayment(BigDecimal amount){

        /*request for payment details*/
        System.out.println("enter the bank name");
        String bankName = this.input.next();

        /*get account name*/
        System.out.println("enter the account name");
        String accountName = this.input.next();

        /*construct a bank payment*/
        Payment payment = new Payment();
        payment.setBankName(bankName);
        payment.setAccountName(accountName);
        payment.setAmount(amount);

        return payment;
    }

    /**
     * process mobile payment.
     * @param amount the amount to process.
     * @return the processed payment*/
    public Payment mobilePayment(BigDecimal amount){

        /*get the mobile number*/
        System.out.println("enter your mobile number;");
        String mobileNumber = this.input.next();

        /*construct and return the payment*/
        Payment payment = new Payment();
        payment.setMobileNumber(mobileNumber);
        payment.setAmount(amount);

        return payment;

    }

    /**
     * process cash payment.
     * @param amount the amount to process.
     * @return the processed payment*/
    public Payment cashPayment(BigDecimal amount){

        /*construct and return the payment*/
        Payment payment = new Payment();
        payment.setAmount(amount);

        return payment;
    }

}
