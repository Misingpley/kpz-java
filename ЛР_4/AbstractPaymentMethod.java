package ua.duikt.holoshchuk.lr4.payment;

import java.util.Locale;

public abstract class AbstractPaymentMethod implements PaymentMethod {
    protected final String providerTitle;
    protected final double feePercent;

    protected AbstractPaymentMethod(String providerTitle, double feePercent) {
        this.providerTitle = providerTitle;
        this.feePercent = feePercent;
    }

    protected void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сума операції має бути додатною: " + amount);
        }
    }

    protected double calculateFee(double amount) {
        return amount * (feePercent / 100.0);
    }

    @Override
    public String methodName() {
        return providerTitle;
    }

    @Override
    public String processPayment(double amount) {
        validateAmount(amount);
        double fee = calculateFee(amount);
        return executeProviderTransaction(amount, fee);
    }

    protected abstract String executeProviderTransaction(double amount, double fee);

    protected String formatStandardRefund(double amount, String destinationInfo) {
        validateAmount(amount);
        return String.format(Locale.US, "Повернення %.2f грн через %s (%s) виконано успішно",
                amount, providerTitle, destinationInfo);
    }
}
