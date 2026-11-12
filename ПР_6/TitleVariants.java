package ua.duikt.holoshchuk.pr6;

import java.util.Objects;

public class TitleVariants {

    public static class TitleNoEquals {
        private String id;
        private final String name;
        private final int year;

        public TitleNoEquals(String id, String name, int year) {
            this.id = id;
            this.name = name;
            this.year = year;
        }
    }

    public static class TitleOnlyEquals {
        private String id;
        private final String name;
        private final int year;

        public TitleOnlyEquals(String id, String name, int year) {
            this.id = id;
            this.name = name;
            this.year = year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TitleOnlyEquals that)) return false;
            return Objects.equals(this.id, that.id);
        }
    }

    public static class TitleProper {
        private String id;
        private final String name;
        private final int year;

        public TitleProper(String id, String name, int year) {
            this.id = id;
            this.name = name;
            this.year = year;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getYear() {
            return year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TitleProper that)) return false;
            return Objects.equals(this.id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.id);
        }

        @Override
        public String toString() {
            return id + " (" + name + ", " + year + ")";
        }
    }
}
