package ua.duikt.holoshchuk.pr8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {

    private Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = new Subscription();
    }

    // Дослід 1: три тести за схемою Given-When-Then
    @Test
    void shouldApplyTenPercentDiscountForSixOrMoreMonths() {
        // Given
        int months = 6;
        // When
        double actualPrice = subscription.price(months);
        // Then: 6 * 199 * 0.9 = 1074.60
        assertEquals(1074.60, actualPrice, 0.001);
    }

    @Test
    void shouldCalculateFullPriceWithoutDiscountForThreeMonths() {
        // Given
        int months = 3;
        // When
        double actualPrice = subscription.price(months);
        // Then: 3 * 199 = 597.00
        assertEquals(597.00, actualPrice, 0.001);
    }

    @Test
    void shouldThrowExceptionWhenMonthsIsZero() {
        // Given
        int invalidMonths = 0;
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> subscription.price(invalidMonths));
    }

    @ParameterizedTest(name = "[{index}] months={0} -> expectedPrice={1}")
    @CsvSource({
            "1,  199.00",
            "5,  995.00",
            "6,  1074.60",
            "12, 2149.20"
    })
    void priceOnValidBoundaries(int months, double expectedPrice) {
        double actual = subscription.price(months);
        assertEquals(expectedPrice, actual, 0.001);
    }

    @ParameterizedTest(name = "[{index}] invalidMonths={0}")
    @ValueSource(ints = {0, 13, -1})
    void shouldRejectOutOfRangeBoundaries(int invalidMonths) {
        assertThrows(IllegalArgumentException.class, () -> subscription.price(invalidMonths));
    }
}
