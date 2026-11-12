package ua.duikt.holoshchuk.lr4.app;

import ua.duikt.holoshchuk.lr4.notification.ClientNotifier;
import ua.duikt.holoshchuk.lr4.notification.EmailNotifier;
import ua.duikt.holoshchuk.lr4.notification.SmsNotifier;
import ua.duikt.holoshchuk.lr4.payment.*;
import ua.duikt.holoshchuk.lr4.service.PaymentService;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        ClientNotifier emailNotifier = new EmailNotifier();
        ClientNotifier smsNotifier = new SmsNotifier();

        System.out.println("=== Конфігурація 1: Банківська картка + Email-сповіщення + Повернення коштів ===");
        CardPayment card = new CardPayment("4149-****-****-8821");
        PaymentService cardEmailService = new PaymentService(card, emailNotifier);
        cardEmailService.pay("vladyslav.holoshchuk@duikt.edu.ua", 1500.00);
        cardEmailService.processRefund(card, "vladyslav.holoshchuk@duikt.edu.ua", 500.00);

        System.out.println("
=== Конфігурація 2: PayPal + SMS-сповіщення (замінено обидві залежності незалежно) ===");
        PayPalPayment payPal = new PayPalPayment("holoshchuk.pay@gmail.com");
        PaymentService payPalSmsService = new PaymentService(payPal, smsNotifier);
        payPalSmsService.pay("+380971234567", 2400.00);
        payPalSmsService.processRefund(payPal, "+380971234567", 400.00);

        System.out.println("
=== Конфігурація 3: Криптовалюта + Email-сповіщення (без інтерфейсу Refundable) ===");
        CryptoPayment crypto = new CryptoPayment("TQn9Y2khEsLJW1ChVWFMSMeRDow5KcbLSE");
        PaymentService cryptoService = new PaymentService(crypto, emailNotifier);
        cryptoService.pay("vladyslav.holoshchuk@duikt.edu.ua", 5000.00);
        // cryptoService.processRefund(crypto, ...);

        System.out.println("
=== Конфігурація 4: Додано нову реалізацію ApplePayPayment (PaymentService.java змінено на 0 рядків) ===");
        ApplePayPayment applePay = new ApplePayPayment("APPL-DEV-901");
        PaymentService applePayService = new PaymentService(applePay, smsNotifier);
        applePayService.pay("+380971234567", 899.00);
    }
}
