package ua.duikt.holoshchuk.lr1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void shouldConvertSpeedBetweenAllUnits() {
        // 10 m/s = 36 km/h
        assertEquals(36.0, Main.convertSpeed(10.0, "m/s", "km/h"), 0.0001);
        // 36 km/h = 10 m/s
        assertEquals(10.0, Main.convertSpeed(36.0, "km/h", "m/s"), 0.0001);
        // 100 mph = 160.9344 km/h
        assertEquals(160.9344, Main.convertSpeed(100.0, "mph", "km/h"), 0.0001);
        // 44.704 m/s = 100 mph
        assertEquals(100.0, Main.convertSpeed(44.704, "m/s", "mph"), 0.0001);
    }

    @Test
    void shouldValidateUnitsCaseInsensitively() {
        assertTrue(Main.isValidSpeedUnit("km/h"));
        assertTrue(Main.isValidSpeedUnit("MPH"));
        assertFalse(Main.isValidSpeedUnit("kg"));
    }
}
