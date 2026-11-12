package ua.duikt.holoshchuk.lr3.domain;

public class ElectricVan extends Transport {
    private final double batteryServiceFeePerTrip;

    public ElectricVan(String model, double kwhPer100Km, double pricePerKwh, double batteryServiceFeePerTrip) {
        super(model, kwhPer100Km, pricePerKwh);
        this.batteryServiceFeePerTrip = Math.max(0.0, batteryServiceFeePerTrip);
    }

    @Override
    public double tripCost(double distanceKm) {
        return baseFuelCost(distanceKm) + batteryServiceFeePerTrip;
    }
}
