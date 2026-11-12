package ua.duikt.holoshchuk.pr1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void shouldReverseStringUsingCommonsLang() {
        assertEquals("avaJ", Main.reverseText("Java"));
    }
}
