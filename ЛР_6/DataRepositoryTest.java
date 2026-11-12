package ua.duikt.holoshchuk.lr6;

import org.junit.jupiter.api.Test;
import ua.duikt.holoshchuk.lr6.domain.StreamingTitle;
import ua.duikt.holoshchuk.lr6.storage.DataRepository;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataRepositoryTest {

    @Test
    void shouldDeduplicateKeysKeepTiesOnSortAndProtectInternalCollection() {
        DataRepository<String, StreamingTitle> repo = new DataRepository<>();
        assertTrue(repo.add(new StreamingTitle("T-1", "Шрек", "фільм", 2001, 8.1)));
        assertTrue(repo.add(new StreamingTitle("T-2", "Гаррі Поттер", "фільм", 2001, 7.8)));
        assertFalse(repo.add(new StreamingTitle("T-1", "Шрек (дублікат)", "фільм", 2001, 9.0)));

        assertEquals(2, repo.size());
        assertEquals("Шрек", repo.findByKey("T-1").getTitle());
        assertNull(repo.findByKey("T-999"));

        List<StreamingTitle> sorted = repo.getSorted(StreamingTitle.BY_YEAR);
        assertEquals(2, sorted.size());

        Iterator<StreamingTitle> it = repo.iterator();
        it.next();
        assertThrows(UnsupportedOperationException.class, it::remove);
    }
}
