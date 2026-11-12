package ua.duikt.holoshchuk.pr3.app;

import ua.duikt.holoshchuk.pr3.model.Developer;
import ua.duikt.holoshchuk.pr3.model.Employee;
import ua.duikt.holoshchuk.pr3.model.Manager;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("=== Дослід 1 та 2: Порядок виклику конструкторів і перевизначення з @Override та super ===");
        System.out.println("Створення об'єкта Manager:");
        Employee empManager = new Manager("Олександр Коваль", 45000.0, 8);

        System.out.println("Створення об'єкта Developer:");
        Employee empDeveloper = new Developer("Владислав Голощук", 42000.0, "Java 21");

        System.out.println("
Виклик перевизначеного методу describe() (всередині викликається super.describe()):");
        System.out.println(empManager.describe());
        System.out.println(empDeveloper.describe());

        // Спроба присвоїти посиланню похідного типу об'єкт базового класу:

        System.out.println("
=== Дослід 3: Поле за типом посилання vs Метод за фактичним типом об'єкта ===");
        Developer concreteDev = new Developer("Марія Сидоренко", 48000.0, "Java / Spring");
        Employee baseRef = concreteDev;

        System.out.println("Звернення до поля через посилання Employee (baseRef.label):   " + baseRef.label);
        System.out.println("Звернення до поля через посилання Developer (concreteDev.label): " + concreteDev.label);
        System.out.println("Виклик методу через посилання Employee (baseRef.describe()):  " + baseRef.describe());
    }
}
