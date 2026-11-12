package ua.duikt.holoshchuk.lr7;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void generateStreamingReport(Path filePath, String searchKey) throws IOException {
        List<TitleRecord> allRows;
        try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            allRows = lines
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(TitleRecord::parse)
                    .toList();
        }

        List<TitleRecord> uniqueTitles = allRows.stream()
                .distinct()
                .toList();

        System.out.printf("Рядків у файлі: %d, унікальних записів: %d%n",
                allRows.size(), uniqueTitles.size());

        Collator ukCollator = Collator.getInstance(Locale.of("uk", "UA"));
        Map<String, DoubleSummaryStatistics> statsByGenre = uniqueTitles.stream()
                .collect(Collectors.groupingBy(
                        TitleRecord::getGenre,
                        () -> new TreeMap<>(ukCollator),
                        Collectors.summarizingDouble(TitleRecord::getRating)
                ));

        System.out.println("Жанри (кількість, середній рейтинг):");
        statsByGenre.forEach((genre, stats) ->
                System.out.printf(Locale.US, "  %-12s %2d  %.2f%n",
                        genre, stats.getCount(), stats.getAverage())
        );

        List<TitleRecord> top5 = uniqueTitles.stream()
                .sorted(Comparator.comparingDouble(TitleRecord::getRating).reversed()
                        .thenComparing(TitleRecord::getId))
                .limit(5)
                .toList();

        System.out.println("Топ-5 за рейтингом:");
        top5.forEach(t ->
                System.out.printf(Locale.US, "  %.1f %s %s%n",
                        t.getRating(), t.getId(), t.getTitle())
        );

        String searchOutput = uniqueTitles.stream()
                .filter(t -> t.getId().equals(searchKey))
                .findFirst()
                .map(TitleRecord::formatSearchResult)
                .orElse("не знайдено");

        System.out.printf("Пошук %s: %s%n", searchKey, searchOutput);
    }

    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        if (args.length < 2) {
            System.out.println("Використання: java -jar report.jar <файл_даних> <ключ_пошуку>");
            return;
        }
        generateStreamingReport(Path.of(args[0]), args[1]);
    }
}
