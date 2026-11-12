package ua.duikt.holoshchuk.lr5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GradeImporterTest {

    @Test
    void shouldParseValidLineAndRejectInvalidOnesWithCause() {
        assertDoesNotThrow(() -> {
            GradeRecord r = GradeRecord.parse("Олійник Дмитро;Математика;50");
            assertEquals("Олійник Дмитро", r.getStudentName());
            assertEquals(50, r.getGrade());
        });

        RecordParseException outOfRange = assertThrows(
                RecordParseException.class,
                () -> GradeRecord.parse("Іваненко Петро;Математика;105")
        );
        assertTrue(outOfRange.getMessage().contains("105"));

        RecordParseException notANumber = assertThrows(
                RecordParseException.class,
                () -> GradeRecord.parse("Коваленко Андрій;Програмування;дев'ять")
        );
        assertNotNull(notANumber.getCause());
        assertInstanceOf(NumberFormatException.class, notANumber.getCause());
    }
}
