package ua.duikt.holoshchuk.pr4.service;

import ua.duikt.holoshchuk.pr4.model.Shape;
import java.util.List;

public interface ReportFormatter {
    String format(List<Shape> shapes);
}
