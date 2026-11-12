package ua.duikt.holoshchuk.pr8;

public class Subscription {
    public static final double MONTHLY_PRICE = 199.0;

    public double price(int months) {
        if (months < 1 || months > 12) {
            throw new IllegalArgumentException("Кількість місяців має бути в діапазоні 1..12, отримано: " + months);
        }
        double total = months * MONTHLY_PRICE;
        if (months >= 6) {
            total *= 0.90; // знижка 10%
        }
        return total;
    }
}
