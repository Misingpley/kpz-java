package ua.duikt.holoshchuk.pr5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlingTest {

    @Test
    void shouldPreserveCauseWhenWrappingNumberFormatException() {
        InvalidGradeException ex = assertThrows(
                InvalidGradeException.class,
                () -> Main.parseGradeWithCause("не_число")
        );
        assertNotNull(ex.getCause());
        assertInstanceOf(NumberFormatException.class, ex.getCause());
    }

    @Test
    void shouldLoseCauseWhenOnlyMessagePassed() {
        InvalidGradeException ex = assertThrows(
                InvalidGradeException.class,
                () -> Main.parseGradeWithoutCause("не_число")
        );
        assertNull(ex.getCause());
    }
}
