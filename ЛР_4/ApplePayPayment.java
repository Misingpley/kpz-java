package ua.duikt.holoshchuk.lr4.payment;

import java.util.Locale;

public class ApplePayPayment extends AbstractPaymentMethod implements Refundable {
    private final String deviceToken;

    public ApplePayPayment(String deviceToken) {
        super("Apple Pay", 1.0);
        this.deviceToken = deviceToken;
    }

    @Override
    protected String executeProviderTransaction(double amount, double fee) {
        return String.format(Locale.US, "Оплата %.2f грн через Apple Pay [токен %s] (комісія 1.0%%: %.2f грн)",
                amount, deviceToken, fee);
    }

    @Override
    public String refund(double amount) {
        return formatStandardRefund(amount, "на прив'язаний рахунок Apple Pay " + deviceToken);
    }
}
