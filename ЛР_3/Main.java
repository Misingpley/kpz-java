package ua.duikt.holoshchuk.lr3.app;

import ua.duikt.holoshchuk.lr3.domain.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Main {

    // і не використовує instanceof або приведення типів
    public static double printFleetTripReport(Transport[] fleet, double distanceKm) {
        System.out.printf(Locale.US, "=== Розрахунок вартості поїздки на відстань %.1f км ===%n", distanceKm);
        double totalFleetCost = 0.0;
        for (Transport vehicle : fleet) {
            double cost = vehicle.tripCost(distanceKm);
            totalFleetCost += cost;
            System.out.printf(Locale.US, "  %-14s (%-20s) -> %8.2f грн%n",
                    vehicle.getClass().getSimpleName(), vehicle.getModel(), cost);
        }
        System.out.printf(Locale.US, "Сумарні витрати автопарку: %.2f грн%n", totalFleetCost);
        return totalFleetCost;
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Transport[] initialFleet = {
                new Car("Toyota Corolla", 7.2, 56.0, 1.20),
                new Truck("MAN TGX 18.500", 28.0, 54.0, 12.0, 3.50),
                new Motorcycle("Yamaha MT-07", 4.3, 56.0, 0.40)
        };

        System.out.println("1) Початковий автопарк із трьох різновидів:");
        printFleetTripReport(initialFleet, 150.0);

        System.out.println("
2) Автопарк після додавання нового різновиду ElectricVan (метод printFleetTripReport не змінено):");
        Transport[] expandedFleet = {
                new Car("Toyota Corolla", 7.2, 56.0, 1.20),
                new Truck("MAN TGX 18.500", 28.0, 54.0, 12.0, 3.50),
                new Motorcycle("Yamaha MT-07", 4.3, 56.0, 0.40),
                new ElectricVan("Renault Kangoo E-Tech", 19.5, 7.50, 45.0)
        };
        printFleetTripReport(expandedFleet, 150.0);
    }
}
