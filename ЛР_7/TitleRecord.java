package ua.duikt.holoshchuk.lr7;

import java.util.Locale;
import java.util.Objects;

public class TitleRecord {
    private final String id;
    private final String title;
    private final String genre;
    private final int year;
    private final double rating;

    public TitleRecord(String id, String title, String genre, int year, double rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public static TitleRecord parse(String line) {
        String[] parts = line.split(";", -1);
        return new TitleRecord(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                Integer.parseInt(parts[3].trim()),
                Double.parseDouble(parts[4].trim())
        );
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public String formatSearchResult() {
        return String.format(Locale.US, "%s (%d), рейтинг %.1f", title, year, rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitleRecord that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
