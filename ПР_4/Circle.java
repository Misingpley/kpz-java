package ua.duikt.holoshchuk.pr4.model;

public class Circle extends Shape implements Drawable, Scalable {
    private double radius;

    public Circle(double radius) {
        super("Коло");
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public String draw() {
        return String.format("Малюємо коло з радіусом %.2f", radius);
    }

    @Override
    public void scale(double factor) {
        this.radius *= factor;
    }

    @Override
    public String describe() {
        return Drawable.super.describe() + " + " + Scalable.super.describe();
    }

    public double getRadius() {
        return radius;
    }
}
