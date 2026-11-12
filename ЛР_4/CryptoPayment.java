package ua.duikt.holoshchuk.lr4.payment;

import java.util.Locale;

public class CryptoPayment extends AbstractPaymentMethod {
    private final String walletAddress;

    public CryptoPayment(String walletAddress) {
        super("Криптовалюта (USDT TRC-20)", 0.5);
        this.walletAddress = walletAddress;
    }

    @Override
    protected String executeProviderTransaction(double amount, double fee) {
        return String.format(Locale.US, "Оплата %.2f грн у криптовалюті з гаманця %s (комісія мережі 0.5%%: %.2f грн)",
                amount, walletAddress, fee);
    }
}
