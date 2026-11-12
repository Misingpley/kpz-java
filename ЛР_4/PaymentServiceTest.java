package ua.duikt.holoshchuk.lr4;

import org.junit.jupiter.api.Test;
import ua.duikt.holoshchuk.lr4.notification.EmailNotifier;
import ua.duikt.holoshchuk.lr4.notification.SmsNotifier;
import ua.duikt.holoshchuk.lr4.payment.*;
import ua.duikt.holoshchuk.lr4.service.PaymentService;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Test
    void shouldSupportIndependentDependenciesAndRefundableMethods() {
        CardPayment card = new CardPayment("4149-****-1234");
        PaymentService service = new PaymentService(card, new EmailNotifier());

        String payOut = service.pay("test@duikt.edu.ua", 1000.0);
        assertTrue(payOut.contains("1000.00"));

        String refundOut = service.processRefund(card, "test@duikt.edu.ua", 200.0);
        assertTrue(refundOut.contains("Повернення 200.00 грн"));
    }

    @Test
    void shouldWorkWithNewImplementationWithoutChangingService() {
        PaymentService service = new PaymentService(new ApplePayPayment("TOKEN-1"), new SmsNotifier());
        assertTrue(service.pay("+380500000000", 300.0).contains("Apple Pay"));
    }
}
