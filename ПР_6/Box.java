package ua.duikt.holoshchuk.pr6;

public class Box<T> {
    private T value;

    public void put(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
