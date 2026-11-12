package ua.duikt.holoshchuk.lr4.notification;

public class EmailNotifier implements ClientNotifier {
    @Override
    public String notifyClient(String recipient, String message) {
        return String.format("Email на [%s]: клієнта сповіщено (%s)", recipient, message);
    }
}
