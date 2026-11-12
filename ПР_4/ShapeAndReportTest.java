package ua.duikt.holoshchuk.pr4;

import org.junit.jupiter.api.Test;
import ua.duikt.holoshchuk.pr4.model.*;
import ua.duikt.holoshchuk.pr4.service.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ShapeAndReportTest {

    @Test
    void shouldCalculateAreaAndResolveDefaultMethodConflict() {
        Circle c = new Circle(10.0);
        assertEquals(Math.PI * 100.0, c.area(), 0.001);
        c.scale(0.5);
        assertEquals(Math.PI * 25.0, c.area(), 0.001);
        assertTrue(c.describe().contains("Drawable") && c.describe().contains("Scalable"));
    }

    @Test
    void shouldSwitchFormattersWithoutChangingReportService() {
        List<Shape> list = List.of(new Rectangle(3.0, 4.0));
        ReportService s1 = new ReportService(new TextFormatter());
        ReportService s2 = new ReportService(new TableFormatter());
        assertTrue(s1.generateReport(list).contains("текстовий формат"));
        assertTrue(s2.generateReport(list).contains("табличний формат"));
    }
}
