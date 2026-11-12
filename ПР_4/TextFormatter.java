package ua.duikt.holoshchuk.pr4.service;

import ua.duikt.holoshchuk.pr4.model.Shape;
import java.util.List;

public class TextFormatter implements ReportFormatter {
    @Override
    public String format(List<Shape> shapes) {
        StringBuilder sb = new StringBuilder("=== Звіт (текстовий формат) ===
");
        for (Shape s : shapes) {
            sb.append("- ").append(s.formatSummary()).append("
");
        }
        return sb.toString().trim();
    }
}
