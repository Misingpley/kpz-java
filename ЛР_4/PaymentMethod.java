package ua.duikt.holoshchuk.lr4.payment;

public interface PaymentMethod {
    String processPayment(double amount);
    String methodName();
}
