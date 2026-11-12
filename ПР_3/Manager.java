package ua.duikt.holoshchuk.pr3.model;

public class Manager extends Employee {
    public String label = "Менеджер (похідне поле Manager)";
    private int teamSize;

    public Manager(String name, double baseSalary, int teamSize) {
        super(name, baseSalary);
        this.teamSize = teamSize;
        System.out.println("  -> [Manager] Конструктор похідного класу: команда з " + teamSize + " осіб");
    }

    @Override
    public String describe() {
        return super.describe() + String.format(" | Посада: Менеджер (команда: %d осіб)", teamSize);
    }
}
