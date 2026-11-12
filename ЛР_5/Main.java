package ua.duikt.holoshchuk.lr5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Locale;

public class Main {

    public static void importGradesFile(Path inputPath, Path outputPath) {
        int imported = 0;
        int rejected = 0;
        long sum = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            if (header == null) {
                System.out.println("Помилка: вхідний файл порожній.");
                return;
            }

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    GradeRecord record = GradeRecord.parse(line);
                    imported++;
                    int g = record.getGrade();
                    sum += g;
                    if (g < min) min = g;
                    if (g > max) max = g;
                } catch (RecordParseException e) {
                    rejected++;
                    String details = e.getMessage();
                    if (e.getCause() != null) {
                        details += " (причина: " + e.getCause().getClass().getSimpleName()
                                + " - " + e.getCause().getMessage() + ")";
                    }
                    System.out.println("Рядок " + lineNumber + ": " + details);
                }
            }
        } catch (NoSuchFileException e) {
            System.out.println("Помилка: файл " + inputPath + " не знайдено");
            return;
        } catch (IOException e) {
            System.out.println("Помилка читання файлу " + inputPath + ": " + e.getMessage());
            return;
        }

        double avg = imported > 0 ? (double) sum / imported : 0.0;
        int finalMin = imported > 0 ? min : 0;
        int finalMax = imported > 0 ? max : 0;

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write("=== Підсумковий звіт імпорту оцінок студентів (Варіант 0) ===
");
            writer.write("Вхідний файл: " + inputPath.getFileName() + "
");
            writer.write("Імпортовано коректних записів: " + imported + "
");
            writer.write("Відхилено пошкоджених рядків: " + rejected + "
");
            writer.write(String.format(Locale.US, "Середній бал: %.2f%n", avg));
            writer.write("Мінімальний бал: " + finalMin + "
");
            writer.write("Максимальний бал: " + finalMax + "
");
        } catch (IOException e) {
            System.out.println("Помилка запису звіту у файл " + outputPath + ": " + e.getMessage());
            return;
        }

        System.out.printf(Locale.US,
                "Імпортовано: %d, відхилено: %d (середній: %.2f, мін: %d, макс: %d). Звіт записано у %s%n",
                imported, rejected, avg, finalMin, finalMax, outputPath);
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        if (args.length < 2) {
            System.out.println("Використання: java -jar importer.jar <вхідний_файл> <вихідний_звіт>");
            return;
        }
        importGradesFile(Path.of(args[0]), Path.of(args[1]));
    }
}
