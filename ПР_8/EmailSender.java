package ua.duikt.holoshchuk.pr8;

public interface EmailSender {
    void sendReceipt(String userEmail, double finalAmount);
}
