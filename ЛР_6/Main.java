package ua.duikt.holoshchuk.lr6.app;

import ua.duikt.holoshchuk.lr6.domain.StreamingTitle;
import ua.duikt.holoshchuk.lr6.domain.StreamingUser;
import ua.duikt.holoshchuk.lr6.storage.DataRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        DataRepository<String, StreamingTitle> titleRepo = new DataRepository<>();
        int titleFileRows = 0;
        try (BufferedReader reader = Files.newBufferedReader(Path.of("v0_titles.txt"), StandardCharsets.UTF_8)) {
            reader.readLine(); // пропускаємо заголовок
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    titleFileRows++;
                    titleRepo.add(StreamingTitle.parse(line));
                }
            }
        }

        System.out.println("=== 1. Сховище тайтлів (v0_titles.txt) ===");
        System.out.println("Тайтли: рядків у файлі " + titleFileRows + ", записів у сховищі " + titleRepo.size());

        StreamingTitle foundTitle = titleRepo.findByKey("T-1017");
        System.out.println("Пошук T-1017: " + (foundTitle != null ? foundTitle : "не знайдено"));
        StreamingTitle missingTitle = titleRepo.findByKey("T-0000");
        System.out.println("Пошук T-0000: " + (missingTitle != null ? missingTitle : "не знайдено"));

        List<StreamingTitle> byYear = titleRepo.getSorted(StreamingTitle.BY_YEAR);
        System.out.println("
За роком виходу (" + byYear.size() + " записів, показано перші 10):");
        for (int i = 0; i < Math.min(10, byYear.size()); i++) {
            StreamingTitle t = byYear.get(i);
            System.out.println("  " + t.getReleaseYear() + " " + t.getId() + " " + t.getTitle());
        }

        DataRepository<String, StreamingUser> userRepo = new DataRepository<>();
        int userFileRows = 0;
        try (BufferedReader reader = Files.newBufferedReader(Path.of("v0_users.txt"), StandardCharsets.UTF_8)) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    userFileRows++;
                    userRepo.add(StreamingUser.parse(line));
                }
            }
        }

        System.out.println("
=== 2. Сховище користувачів (v0_users.txt) ===");
        System.out.println("Користувачі: рядків у файлі " + userFileRows + ", записів у сховищі " + userRepo.size());

        StreamingUser foundUser = userRepo.findByKey("nova36");
        System.out.println("Пошук nova36: " + (foundUser != null ? foundUser : "не знайдено"));
        StreamingUser missingUser = userRepo.findByKey("user_000");
        System.out.println("Пошук user_000: " + (missingUser != null ? missingUser : "не знайдено"));

        List<StreamingUser> byRegDate = userRepo.getSorted(StreamingUser.BY_REGISTRATION_DATE);
        System.out.println("
За датою реєстрації (" + byRegDate.size() + " записів, показано перші 10):");
        for (int i = 0; i < Math.min(10, byRegDate.size()); i++) {
            StreamingUser u = byRegDate.get(i);
            System.out.println("  " + u.getRegistrationDate() + " " + u.getLogin() + " - " + u.getFullName());
        }

        int countIterated = 0;
        for (StreamingTitle t : titleRepo) {
            countIterated++;
        }
        System.out.println("
Обхід titleRepo циклом for-each успішний, пройдено записів: " + countIterated);
    }
}
