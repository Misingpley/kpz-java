package ua.duikt.holoshchuk.lr3.domain;

public class Truck extends Transport {
    private final double cargoTons;
    private final double roadTollPerKm;

    public Truck(String model, double litersPer100Km, double dieselPrice, double cargoTons, double roadTollPerKm) {
        super(model, litersPer100Km, dieselPrice);
        if (cargoTons < 0 || roadTollPerKm < 0) {
            throw new IllegalArgumentException("Маса вантажу та дорожній збір не можуть бути від'ємними");
        }
        this.cargoTons = cargoTons;
        this.roadTollPerKm = roadTollPerKm;
    }

    @Override
    public double tripCost(double distanceKm) {
        // Кожна тонна вантажу збільшує витрату пального на 3%
        double loadMultiplier = 1.0 + (cargoTons * 0.03);
        return baseFuelCost(distanceKm) * loadMultiplier + distanceKm * roadTollPerKm;
    }
}
