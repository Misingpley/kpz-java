package ua.duikt.holoshchuk.pr5;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void openMissingFileWithThrows(String filename) throws FileNotFoundException {
        FileReader reader = new FileReader(filename);
    }

    public static int parseGradeWithoutCause(String raw) throws InvalidGradeException {
        try {
            int grade = Integer.parseInt(raw);
            if (grade < 0 || grade > 100) {
                throw new InvalidGradeException("Оцінка поза межами 0..100: " + grade);
            }
            return grade;
        } catch (NumberFormatException e) {
            throw new InvalidGradeException(e.getMessage());
        }
    }

    public static int parseGradeWithCause(String raw) throws InvalidGradeException {
        try {
            int grade = Integer.parseInt(raw);
            if (grade < 0 || grade > 100) {
                throw new InvalidGradeException("Оцінка поза межами 0..100: " + grade);
            }
            return grade;
        } catch (NumberFormatException e) {
            throw new InvalidGradeException("Некоректна оцінка студента: "" + raw + """, e);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("=== Дослід 1: Перевірювані й неперевірювані винятки, порядок catch і блок finally ===");
        // 1) Спроба відкрити файл без try-catch або throws:

        try {
            openMissingFileWithThrows("nonexistent_grades.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Перехоплено перевірюваний виняток FileNotFoundException: " + e.getMessage());
        } finally {
            System.out.println("Блок finally №1 (виконався після винятку FileNotFoundException)");
        }

        try {
            System.out.println("Успішна операція без помилок.");
        } finally {
            System.out.println("Блок finally №2 (виконався за відсутності винятків)");
        }

        try {
            int[] grades = {95, 88};
            int invalidAccess = grades[5];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Перехоплено неперевірюваний виняток під час виконання: " + e);
        }

        System.out.println("
=== Дослід 2: Власний виняток InvalidGradeException (з причиною та без неї) ===");
        try {
            parseGradeWithoutCause("abc");
        } catch (InvalidGradeException e) {
            System.out.println("Стектрейс БЕЗ збереження причини (new InvalidGradeException(e.getMessage())):");
            e.printStackTrace(System.out);
        }

        try {
            parseGradeWithCause("abc");
        } catch (InvalidGradeException e) {
            System.out.println("
Стектрейс ЗІ збереженням причини (new InvalidGradeException("...", e)):");
            e.printStackTrace(System.out);
        }

        System.out.println("
=== Дослід 3: Закриття ресурсів (BufferedWriter) та кодування UTF-8 vs windows-1251 ===");
        Path unclosedFile = Path.of("grades_unclosed.txt");
        BufferedWriter unclosedWriter = Files.newBufferedWriter(unclosedFile, StandardCharsets.UTF_8);
        unclosedWriter.write("Голощук Владислав;Конструювання ПЗ Java;95
");
        unclosedWriter.write("Коваленко Андрій;Бази даних;88
");
        System.out.println("Розмір файлу grades_unclosed.txt без close(): " + Files.size(unclosedFile) + " байтів");

        Path closedFile = Path.of("grades_utf8.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(closedFile, StandardCharsets.UTF_8)) {
            writer.write("Голощук Владислав;Конструювання ПЗ Java;95
");
            writer.write("Коваленко Андрій;Бази даних;88
");
        }
        System.out.println("Розмір файлу grades_utf8.txt після try-with-resources: " + Files.size(closedFile) + " байтів");

        System.out.println("
Читання файлу з явним кодуванням UTF-8:");
        try (BufferedReader reader = Files.newBufferedReader(closedFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("  [UTF-8] " + line);
            }
        }

        System.out.println("
Читання того самого файлу з помилковим кодуванням windows-1251:");
        try (BufferedReader reader = Files.newBufferedReader(closedFile, Charset.forName("windows-1251"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("  [windows-1251] " + line);
            }
        }
    }
}
