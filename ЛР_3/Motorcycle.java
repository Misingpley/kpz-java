package ua.duikt.holoshchuk.lr3.domain;

public class Motorcycle extends Transport {
    private final double tireWearPerKm;

    public Motorcycle(String model, double litersPer100Km, double fuelPricePerLiter, double tireWearPerKm) {
        super(model, litersPer100Km, fuelPricePerLiter);
        if (tireWearPerKm < 0) {
            throw new IllegalArgumentException("Знос шин не може бути від'ємним");
        }
        this.tireWearPerKm = tireWearPerKm;
    }

    @Override
    public double tripCost(double distanceKm) {
        return baseFuelCost(distanceKm) + distanceKm * tireWearPerKm;
    }
}
