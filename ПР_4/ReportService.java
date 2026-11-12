package ua.duikt.holoshchuk.pr4.service;

import ua.duikt.holoshchuk.pr4.model.Shape;
import java.util.List;

public class ReportService {
    private final ReportFormatter formatter;

    public ReportService(ReportFormatter formatter) {
        this.formatter = formatter;
    }

    public String generateReport(List<Shape> shapes) {
        return formatter.format(shapes);
    }
}
