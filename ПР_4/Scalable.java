package ua.duikt.holoshchuk.pr4.model;

public interface Scalable {
    void scale(double factor);

    default String describe() {
        return "[Scalable: підтримує масштабування розмірів]";
    }
}
