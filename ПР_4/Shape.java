package ua.duikt.holoshchuk.pr4.model;

public abstract class Shape {
    protected final String name;

    protected Shape(String name) {
        this.name = name;
    }

    public abstract double area();

    // Звичайний неабстрактний метод
    public String formatSummary() {
        return String.format("Фігура '%s', площа = %.2f", name, area());
    }
}
