package ua.duikt.holoshchuk.pr3.model;

public class Employee {
    public String label = "Працівник (базове поле Employee)";

    protected String name;
    protected double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
        System.out.println("  -> [Employee] Конструктор базового класу: ініціалізовано " + name);
    }

    public String describe() {
        return String.format("Працівник: %s, базовий оклад: %.2f грн", name, baseSalary);
    }
}
