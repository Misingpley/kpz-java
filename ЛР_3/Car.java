package ua.duikt.holoshchuk.lr3.domain;

public class Car extends Transport {
    private final doublewearAndTearPerKm;

    public Car(String model, double litersPer100Km, double fuelPricePerLiter, double wearAndTearPerKm) {
        super(model, litersPer100Km, fuelPricePerLiter);
        if (wearAndTearPerKm < 0) {
            throw new IllegalArgumentException("Амортизація не може бути від'ємною");
        }
        this.doublewearAndTearPerKm = wearAndTearPerKm;
    }

    @Override
    public double tripCost(double distanceKm) {
        return baseFuelCost(distanceKm) + distanceKm * doublewearAndTearPerKm;
    }
}
