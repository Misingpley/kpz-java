package ua.duikt.holoshchuk.pr2;

import org.junit.jupiter.api.Test;
import ua.duikt.holoshchuk.pr2.model.Book;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void shouldChainConstructorsAndKeepStaticCounter() {
        int before = Book.getTotalCreatedBooks();
        Book b1 = new Book();
        Book b2 = new Book("Лісова пісня", "Леся Українка");
        assertEquals("Кобзар (типове видання)", b1.title);
        assertEquals(2026, b2.getYear());
        assertEquals(before + 2, Book.getTotalCreatedBooks());
    }

    @Test
    void shouldDemonstrateShadowingWithAndWithoutThis() {
        Book b = new Book("Початкова", "Автор", 2020);
        b.setTitleWithoutThis("Ігнорована");
        assertEquals("Початкова", b.title);
        b.setTitleWithThis("Оновлена");
        assertEquals("Оновлена", b.title);
    }
}
