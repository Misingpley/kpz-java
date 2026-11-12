package ua.duikt.holoshchuk.pr4.model;

public interface Drawable {
    String draw();

    default String describe() {
        return "[Drawable: підтримує візуальне малювання на полотні]";
    }
}
