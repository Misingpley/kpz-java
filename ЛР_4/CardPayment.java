package ua.duikt.holoshchuk.lr4.payment;

import java.util.Locale;

public class CardPayment extends AbstractPaymentMethod implements Refundable {
    private final String maskedCardNumber;

    public CardPayment(String maskedCardNumber) {
        super("Банківська картка", 1.5);
        this.maskedCardNumber = maskedCardNumber;
    }

    @Override
    protected String executeProviderTransaction(double amount, double fee) {
        return String.format(Locale.US, "Оплата %.2f грн карткою %s (комісія еквайрингу 1.5%%: %.2f грн)",
                amount, maskedCardNumber, fee);
    }

    @Override
    public String refund(double amount) {
        return formatStandardRefund(amount, "на картку " + maskedCardNumber);
    }
}
