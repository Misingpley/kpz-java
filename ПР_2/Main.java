package ua.duikt.holoshchuk.pr2.app;

import ua.duikt.holoshchuk.pr2.model.Book;
import ua.duikt.holoshchuk.pr2.model.ModelPackageInspector;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("=== Дослід 1: Перевірка модифікаторів доступу ===");
        Book b1 = new Book("Тигролови", "Іван Багряний", 1944);
        System.out.println(ModelPackageInspector.inspectFromSamePackage(b1));

        // Звернення з класу Main (пакет app):
        System.out.println("[З пакета app] public b1.title = " + b1.title);

        System.out.println("
=== Дослід 2: Три види конструкторів та виклик this(...) ===");
        Book defaultBook = new Book();
        Book twoArgBook = new Book("Захар Беркут", "Іван Франко");
        Book fullBook = new Book("Місто", "Валер'ян Підмогильний", 1928);
        System.out.println("Конструктор без параметрів: " + defaultBook);
        System.out.println("Конструктор з 2 параметрами: " + twoArgBook);
        System.out.println("Повний конструктор (3 параметри): " + fullBook);

        System.out.println("
=== Дослід 3: Ключове слово this та затінення імен ===");
        fullBook.setTitleWithoutThis("Нова назва (без this)");
        System.out.println("Після setTitleWithoutThis: title = " + fullBook.title + " (поле не змінилося!)");
        fullBook.setTitleWithThis("Місто (оновлене видання)");
        System.out.println("Після setTitleWithThis: title = " + fullBook.title + " (поле успішно змінено)");

        System.out.println("
=== Дослід 4: Порівняння static-поля та звичайного поля екземпляра ===");
        System.out.println("Book.count (static, через ім'я класу) = " + Book.getTotalCreatedBooks());
        System.out.println("b1.title = " + b1.title + " | defaultBook.title = " + defaultBook.title + " | twoArgBook.title = " + twoArgBook.title);

        System.out.println("
=== Дослід 5: Незмінність final-поля та final-змінної ===");
        final int maxAllowedCopies = 5;
        System.out.println("final-поле b1.id = " + b1.id + ", локальна final-змінна maxAllowedCopies = " + maxAllowedCopies);
    }
}
