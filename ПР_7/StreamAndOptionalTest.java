package ua.duikt.holoshchuk.pr7;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StreamAndOptionalTest {

    @Test
    void shouldProduceSameResultInLoopAndStream() {
        List<Title> list = List.of(
                new Title("T-1", "Матриця", 1999),
                new Title("T-2", "Аркейн", 2021),
                new Title("T-3", "Довбуш", 2023)
        );
        assertEquals(Main.titlesAfter2015Loop(list), Main.titlesAfter2015Stream(list));
        assertEquals(List.of("Аркейн", "Довбуш"), Main.titlesAfter2015Stream(list));
    }
}
