package ua.duikt.holoshchuk.lr4.service;

import ua.duikt.holoshchuk.lr4.notification.ClientNotifier;
import ua.duikt.holoshchuk.lr4.payment.PaymentMethod;
import ua.duikt.holoshchuk.lr4.payment.Refundable;

public class PaymentService {
    private final PaymentMethod paymentMethod;
    private final ClientNotifier clientNotifier;

    public PaymentService(PaymentMethod paymentMethod, ClientNotifier clientNotifier) {
        if (paymentMethod == null || clientNotifier == null) {
            throw new IllegalArgumentException("Залежності PaymentService не можуть бути null");
        }
        this.paymentMethod = paymentMethod;
        this.clientNotifier = clientNotifier;
    }

    public String pay(String clientContact, double amount) {
        String txResult = paymentMethod.processPayment(amount);
        String notifyResult = clientNotifier.notifyClient(clientContact, txResult);
        System.out.println("  -> " + txResult);
        System.out.println("  -> " + notifyResult);
        return txResult;
    }

    public String processRefund(Refundable refundableMethod, String clientContact, double amount) {
        String refundResult = refundableMethod.refund(amount);
        String notifyResult = clientNotifier.notifyClient(clientContact, refundResult);
        System.out.println("  -> " + refundResult);
        System.out.println("  -> " + notifyResult);
        return refundResult;
    }
}
