package ua.duikt.holoshchuk.pr8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RenewalServiceTest {

    @Mock
    private PriceList priceList;     // виконує роль стаба (постачає тариф)

    @Mock
    private EmailSender emailSender; // виконує роль мока (перевіряємо факт виклику)

    @InjectMocks
    private RenewalService renewalService;

    @Test
    void shouldCalculateDiscountedPriceFromStubAndSendReceiptToMock() {
        // Given (налаштовуємо стаб PriceList)
        when(priceList.getMonthlyRate("STANDARD")).thenReturn(199.0);

        // When
        double total = renewalService.renewSubscription("student@duikt.edu.ua", "STANDARD", 6);

        // Then
        assertEquals(1074.60, total, 0.001);
        verify(emailSender).sendReceipt("student@duikt.edu.ua", 1074.60);
    }
}
