package ua.duikt.holoshchuk.pr4.service;

import ua.duikt.holoshchuk.pr4.model.Shape;
import java.util.List;

public class TableFormatter implements ReportFormatter {
    @Override
    public String format(List<Shape> shapes) {
        StringBuilder sb = new StringBuilder("=== Звіт (табличний формат) ===
");
        sb.append(String.format("| %-28s |
", "Опис фігури та площа"));
        sb.append("|------------------------------|
");
        for (Shape s : shapes) {
            sb.append(String.format("| %-28s |
", s.formatSummary()));
        }
        return sb.toString().trim();
    }
}
