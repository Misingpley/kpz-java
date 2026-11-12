package ua.duikt.holoshchuk.pr6;

public class ObjectBox {
    private Object value;

    public void put(Object value) {
        this.value = value;
    }

    public Object get() {
        return value;
    }
}
