package ua.duikt.holoshchuk.pr4.app;

import ua.duikt.holoshchuk.pr4.model.*;
import ua.duikt.holoshchuk.pr4.service.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("=== Дослід 1: Абстрактний клас Shape та похідні Circle і Rectangle ===");
        Circle circle = new Circle(5.0);
        Rectangle rect = new Rectangle(4.0, 6.0);
        System.out.println(circle.formatSummary());
        System.out.println(rect.formatSummary());

        System.out.println("
=== Дослід 2: Робота через посилання інтерфейсів Drawable і Scalable та розв'язання конфлікту default-методів ===");
        Drawable d = circle;
        System.out.println("Виклик через Drawable: " + d.draw());

        Scalable s = circle;
        s.scale(2.0);
        System.out.println("Після s.scale(2.0) через Scalable: " + circle.formatSummary());
        System.out.println("Результат розв'язання конфлікту default-методу describe(): " + circle.describe());

        System.out.println("
=== Дослід 3: Передача залежності через конструктор у ReportService ===");
        List<Shape> shapes = List.of(circle, rect);

        ReportService textReportService = new ReportService(new TextFormatter());
        System.out.println(textReportService.generateReport(shapes));

        System.out.println();
        ReportService tableReportService = new ReportService(new TableFormatter());
        System.out.println(tableReportService.generateReport(shapes));
    }
}
