package ua.duikt.holoshchuk.pr3.model;

public class Developer extends Employee {
    public String label = "Розробник (похідне поле Developer)";
    private String primaryLanguage;

    public Developer(String name, double baseSalary, String primaryLanguage) {
        super(name, baseSalary);
        this.primaryLanguage = primaryLanguage;
        System.out.println("  -> [Developer] Конструктор похідного класу: мова " + primaryLanguage);
    }

    @Override
    public String describe() {
        return super.describe() + String.format(" | Посада: Розробник (стек: %s)", primaryLanguage);
    }
}
