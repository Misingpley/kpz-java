package ua.duikt.holoshchuk.lr6.domain;

import ua.duikt.holoshchuk.lr6.storage.Identifiable;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class StreamingTitle implements Identifiable<String> {
    private final String id;
    private final String title;
    private final String type;
    private final int releaseYear;
    private final double rating;

    public StreamingTitle(String id, String title, String type, int releaseYear, double rating) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public static StreamingTitle parse(String line) {
        String[] parts = line.split(";", -1);
        return new StreamingTitle(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                Integer.parseInt(parts[3].trim()),
                Double.parseDouble(parts[4].trim())
        );
    }

    @Override
    public String getKey() {
        return id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public static final Comparator<StreamingTitle> BY_YEAR = new Comparator<StreamingTitle>() {
        @Override
        public int compare(StreamingTitle a, StreamingTitle b) {
            int cmp = Integer.compare(a.releaseYear, b.releaseYear);
            if (cmp != 0) return cmp;
            return a.id.compareTo(b.id);
        }
    };

    // Компаратор за рейтингом (за спаданням)
    public static final Comparator<StreamingTitle> BY_RATING_DESC = new Comparator<StreamingTitle>() {
        @Override
        public int compare(StreamingTitle a, StreamingTitle b) {
            int cmp = Double.compare(b.rating, a.rating);
            if (cmp != 0) return cmp;
            return a.id.compareTo(b.id);
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreamingTitle that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s: %s (%s, %d, рейтинг %.1f)", id, title, type, releaseYear, rating);
    }
}
