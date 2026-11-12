package ua.duikt.holoshchuk.pr8;

public class RenewalService {
    private final PriceList priceList;
    private final EmailSender emailSender;

    public RenewalService(PriceList priceList, EmailSender emailSender) {
        this.priceList = priceList;
        this.emailSender = emailSender;
    }

    public double renewSubscription(String userEmail, String planCode, int months) {
        if (months < 1 || months > 12) {
            throw new IllegalArgumentException("Некоректна тривалість підписки: " + months);
        }
        double monthlyRate = priceList.getMonthlyRate(planCode);
        double total = monthlyRate * months;
        if (months >= 6) {
            total *= 0.90;
        }
        emailSender.sendReceipt(userEmail, total);
        return total;
    }
}
