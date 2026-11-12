package ua.duikt.holoshchuk.pr2.model;

public class Book {
    // Дослід 1: чотири рівні доступу
    private String internalNote;       // доступний лише всередині класу Book
    String catalogCode;                // доступ за замовчуванням (package-private) - у межах пакета model
    protected int year;                // доступний у пакеті model та у підкласах
    public String title;               // доступний звідусіль

    private String author;

    // Дослід 4: статичне поле (спільне для всіх об'єктів)
    public static int count = 0;

    public final int id;

    public Book() {
        this("Кобзар (типове видання)", "Тарас Шевченко", 2024);
    }

    public Book(String title, String author) {
        this(title, author, 2026);
    }

    public Book(String title, String author, int year) {
        count++;
        this.id = count;
        this.title = title;
        this.author = author;
        this.year = year;
        this.catalogCode = "CAT-" + String.format("%03d", this.id);
        this.internalNote = "Службовий запис книги #" + this.id;
    }

    public void setTitleWithoutThis(String title) {
        title = title;
    }

    public void setTitleWithThis(String title) {
        this.title = title;
    }

    // Дослід 4: статичний метод
    public static int getTotalCreatedBooks() {
        return count;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getInternalNote() {
        return internalNote;
    }

    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', year=%d, code='%s'}",
                id, title, author, year, catalogCode);
    }
}
