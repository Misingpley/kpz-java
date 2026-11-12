package ua.duikt.holoshchuk.lr4.notification;

public class SmsNotifier implements ClientNotifier {
    @Override
    public String notifyClient(String recipient, String message) {
        return String.format("SMS на [%s]: клієнта сповіщено (%s)", recipient, message);
    }
}
