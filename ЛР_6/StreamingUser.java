package ua.duikt.holoshchuk.lr6.domain;

import ua.duikt.holoshchuk.lr6.storage.Identifiable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class StreamingUser implements Identifiable<String> {
    private final String login;
    private final String fullName;
    private final LocalDate registrationDate;

    public StreamingUser(String login, String fullName, LocalDate registrationDate) {
        this.login = login;
        this.fullName = fullName;
        this.registrationDate = registrationDate;
    }

    public static StreamingUser parse(String line) {
        String[] parts = line.split(";", -1);
        return new StreamingUser(
                parts[0].trim(),
                parts[1].trim(),
                LocalDate.parse(parts[2].trim())
        );
    }

    @Override
    public String getKey() {
        return login;
    }

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public static final Comparator<StreamingUser> BY_REGISTRATION_DATE = new Comparator<StreamingUser>() {
        @Override
        public int compare(StreamingUser a, StreamingUser b) {
            int cmp = a.registrationDate.compareTo(b.registrationDate);
            if (cmp != 0) return cmp;
            return a.login.compareTo(b.login);
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreamingUser that)) return false;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(login);
    }

    @Override
    public String toString() {
        return String.format("%s (%s, зареєстровано %s)", login, fullName, registrationDate);
    }
}
