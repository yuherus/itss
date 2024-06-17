package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Payment {
    public enum PaymentMethod {
        CASH, CREDIT_CARD, E_WALLET
    }
    private int paymentId;
    private Tour tour;

    private User tourist;

    private double amount;

    private Timestamp paymentDate;

    private PaymentMethod paymentMethod;

    public Payment() {
    }

    public Payment(Tour tour, User tourist, double amount, Timestamp paymentDate, PaymentMethod paymentMethod) {
        this.tour = tour;
        this.tourist = tourist;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public User getTourist() {
        return tourist;
    }

    public void setTourist(User tourist) {
        this.tourist = tourist;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
