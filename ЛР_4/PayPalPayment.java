package ua.duikt.holoshchuk.lr4.payment;

import java.util.Locale;

public class PayPalPayment extends AbstractPaymentMethod implements Refundable {
    private final String accountEmail;

    public PayPalPayment(String accountEmail) {
        super("PayPal", 2.9);
        this.accountEmail = accountEmail;
    }

    @Override
    protected String executeProviderTransaction(double amount, double fee) {
        return String.format(Locale.US, "Оплата %.2f грн через акаунт PayPal [%s] (комісія 2.9%%: %.2f грн)",
                amount, accountEmail, fee);
    }

    @Override
    public String refund(double amount) {
        return formatStandardRefund(amount, "на гаманець PayPal " + accountEmail);
    }
}
