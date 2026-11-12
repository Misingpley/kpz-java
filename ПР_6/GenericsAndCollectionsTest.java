package ua.duikt.holoshchuk.pr6;

import org.junit.jupiter.api.Test;
import ua.duikt.holoshchuk.pr6.TitleVariants.TitleProper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GenericsAndCollectionsTest {

    @Test
    void shouldAcceptIntegerAndDoubleListsWithUpperBoundedWildcard() {
        assertEquals(60.0, Main.sumNumbers(Arrays.asList(10, 20, 30)), 0.001);
        assertEquals(10.0, Main.sumNumbers(Arrays.asList(2.5, 7.5)), 0.001);
    }

    @Test
    void shouldDeduplicateInHashSetWhenEqualsAndHashCodeImplemented() {
        Set<TitleProper> set = new HashSet<>();
        set.add(new TitleProper("T-1", "Матриця", 1999));
        set.add(new TitleProper("T-1", "Матриця (копія)", 1999));
        assertEquals(1, set.size());
        assertTrue(set.contains(new TitleProper("T-1", "Будь-яка назва", 2020)));
    }
}
