package ua.duikt.holoshchuk.lr7;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StreamReportTest {

    @Test
    void shouldGenerateReportForExistingAndMissingKey() {
        assertDoesNotThrow(() -> Main.generateStreamingReport(Path.of("v0_titles.txt"), "T-1651"));
        assertDoesNotThrow(() -> Main.generateStreamingReport(Path.of("v0_titles.txt"), "T-0000"));
    }
}
