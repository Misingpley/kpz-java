package ua.duikt.holoshchuk.pr4.model;

public class Rectangle extends Shape implements Drawable, Scalable {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super("Прямокутник");
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public String draw() {
        return String.format("Малюємо прямокутник %.2f x %.2f", width, height);
    }

    @Override
    public void scale(double factor) {
        this.width *= factor;
        this.height *= factor;
    }

    @Override
    public String describe() {
        return Drawable.super.describe() + " + " + Scalable.super.describe();
    }
}
