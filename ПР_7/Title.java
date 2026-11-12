package ua.duikt.holoshchuk.pr7;

public class Title {
    private final String id;
    private final String name;
    private final int year;

    public Title(String id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %d)", name, id, year);
    }
}
