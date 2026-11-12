package ua.duikt.holoshchuk.lr3.domain;

public abstract class Transport {
    protected final String model;
    protected final double consumptionPer100Km;
    protected final double unitFuelPrice;

    protected Transport(String model, double consumptionPer100Km, double unitFuelPrice) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Модель транспорту не може бути порожньою");
        }
        if (consumptionPer100Km <= 0 || unitFuelPrice <= 0) {
            throw new IllegalArgumentException("Витрата та ціна пального мають бути додатними");
        }
        this.model = model.trim();
        this.consumptionPer100Km = consumptionPer100Km;
        this.unitFuelPrice = unitFuelPrice;
    }

    protected void validateDistance(double distanceKm) {
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Відстань поїздки має бути більшою за 0 км: " + distanceKm);
        }
    }

    protected double baseFuelCost(double distanceKm) {
        validateDistance(distanceKm);
        return (distanceKm / 100.0) * consumptionPer100Km * unitFuelPrice;
    }

    public abstract double tripCost(double distanceKm);

    public String getModel() {
        return model;
    }
}
