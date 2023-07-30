package persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * a class to represent a bill.
 * @author kamar baraka*/

@Entity
public class Bill {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Customer customer;

    @ManyToOne
    private Payment payment;

    @OneToMany
    private List<Item> itemList = new ArrayList<>();

    private BigDecimal amount;

    private final LocalDate dateOfIssue = LocalDate.now();

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public List<Item> getItemList() {
        return this.itemList;
    }

    public void setItems(Item... items) {

        this.itemList.addAll(List.of(items));

        if (this.itemList.size() > 2){
        List<BigDecimal> priceList = new ArrayList<>();
            for (Item item : this.itemList)
                priceList.add(item.getPrice());
            this.amount = (BigDecimal) priceList.stream().reduce(BigDecimal::add).stream().toArray()[0];
        }

        this.amount = itemList.get(0).getPrice();
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
