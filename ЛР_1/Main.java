package ua.duikt.holoshchuk.lr1;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    // перевірка одиниці
    public static boolean isValidSpeedUnit(String unit) {
        if (unit == null) return false;
        String u = unit.trim().toLowerCase(Locale.ROOT);
        return u.equals("m/s") || u.equals("km/h") || u.equals("mph");
    }

    // переводимо через м/с
    public static double convertSpeed(double value, String fromUnit, String toUnit) {
        String from = fromUnit.trim().toLowerCase(Locale.ROOT);
        String to = toUnit.trim().toLowerCase(Locale.ROOT);

        double inMetersPerSec;
        if (from.equals("m/s")) {
            inMetersPerSec = value;
        } else if (from.equals("km/h")) {
            inMetersPerSec = value / 3.6;
        } else {
            inMetersPerSec = value * 0.44704; // mph -> m/s
        }

        if (to.equals("m/s")) {
            return inMetersPerSec;
        } else if (to.equals("km/h")) {
            return inMetersPerSec * 3.6;
        } else {
            return inMetersPerSec / 0.44704; // m/s -> mph
        }
    }

    public static boolean isValidAreaUnit(String unit) {
        if (unit == null) return false;
        String u = unit.trim().toLowerCase(Locale.ROOT);
        return u.equals("cm2") || u.equals("m2") || u.equals("a") || u.equals("ha") || u.equals("acre");
    }

    // переводимо через м2
    public static double convertArea(double value, String fromUnit, String toUnit) {
        String from = fromUnit.trim().toLowerCase(Locale.ROOT);
        String to = toUnit.trim().toLowerCase(Locale.ROOT);

        double inSqMeters;
        if (from.equals("cm2")) inSqMeters = value * 0.0001;
        else if (from.equals("m2")) inSqMeters = value;
        else if (from.equals("a")) inSqMeters = value * 100.0;
        else if (from.equals("ha")) inSqMeters = value * 10_000.0;
        else inSqMeters = value * 4046.8564224; // acre

        if (to.equals("cm2")) return inSqMeters / 0.0001;
        else if (to.equals("m2")) return inSqMeters;
        else if (to.equals("a")) return inSqMeters / 100.0;
        else if (to.equals("ha")) return inSqMeters / 10_000.0;
        else return inSqMeters / 4046.8564224;
    }

    // вибір меню
    private static int readMenuChoice(Scanner scanner) {
        while (true) {
            System.out.print("Вибір (1 - Швидкість [Вар. 5], 2 - Площа [Вар. 6], 0 - Вихід): ");
            if (!scanner.hasNext()) {
                return 0;
            }
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 0 && choice <= 2) {
                    return choice;
                }
                System.out.println("-> Помилка: оберіть пункт 0, 1 або 2.");
            } else {
                String wrongToken = scanner.nextLine();
                System.out.println("-> Помилка: "" + wrongToken + "" не є цілим числом. Спробуйте ще раз.");
            }
        }
    }

    // ввід числа
    private static double readNonNegativeDouble(Scanner scanner) {
        while (true) {
            System.out.print("Значення: ");
            if (scanner.hasNextDouble()) {
                double val = scanner.nextDouble();
                scanner.nextLine();
                if (val >= 0.0) {
                    return val;
                }
                System.out.println("-> Помилка: значення не може бути від'ємним. Спробуйте ще раз.");
            } else {
                String wrongToken = scanner.nextLine();
                System.out.println("-> Помилка: "" + wrongToken + "" не є числом. Введіть число та спробуйте ще раз.");
            }
        }
    }

    private static String readValidUnit(Scanner scanner, String prompt, boolean isSpeed) {
        while (true) {
            System.out.print(prompt);
            String unit = scanner.nextLine().trim();
            boolean valid = isSpeed ? isValidSpeedUnit(unit) : isValidAreaUnit(unit);
            if (valid) {
                return unit.toLowerCase(Locale.ROOT);
            }
            System.out.println("-> Помилка: невідома одиниця "" + unit + "". Спробуйте ще раз.");
        }
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        scanner.useLocale(Locale.US);

        System.out.println("=== Конвертер одиниць вимірювання (Варіант 5: Швидкість) ===");
        while (true) {
            int choice = readMenuChoice(scanner);
            if (choice == 0) {
                System.out.println("-> Вихід. Дякуємо за використання!");
                break;
            }
            boolean isSpeed = (choice == 1);
            double value = readNonNegativeDouble(scanner);
            String unitHint = isSpeed ? "(m/s, km/h, mph)" : "(cm2, m2, a, ha, acre)";
            String fromUnit = readValidUnit(scanner, "З " + unitHint + ": ", isSpeed);
            String toUnit = readValidUnit(scanner, "У " + unitHint + ": ", isSpeed);

            double result = isSpeed
                    ? convertSpeed(value, fromUnit, toUnit)
                    : convertArea(value, fromUnit, toUnit);
            System.out.printf(Locale.US, "-> %.4f %s = %.4f %s%n%n", value, fromUnit, result, toUnit);
        }
    }
}
