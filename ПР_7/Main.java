package ua.duikt.holoshchuk.pr7;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static List<String> titlesAfter2015Loop(List<Title> titles) {
        List<String> result = new ArrayList<>();
        for (Title t : titles) {
            if (t.getYear() > 2015) {
                result.add(t.getName());
            }
        }
        return result;
    }

    // Дослід 2: те саме обчислення одним виразом Stream API
    public static List<String> titlesAfter2015Stream(List<Title> titles) {
        return titles.stream()
                .filter(t -> t.getYear() > 2015)
                .map(Title::getName)
                .toList();
    }

    private static List<Title> createSampleList() {
        return new ArrayList<>(List.of(
                new Title("T-1017", "Назад у майбутнє", 1985),
                new Title("T-2521", "Матриця", 1999),
                new Title("T-6145", "Шрек", 2001),
                new Title("T-7002", "Аватар", 2009),
                new Title("T-1195", "Інтерстеллар", 2014),
                new Title("T-7161", "Зоотрополіс", 2016),
                new Title("T-9496", "Тор: Рагнарок", 2017),
                new Title("T-9980", "Аркейн", 2021),
                new Title("T-5785", "Довбуш", 2023),
                new Title("T-6389", "Дюна: Частина друга", 2024)
        ));
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("=== Дослід 1: Три способи сортування за роком виходу ===");
        List<Title> list1 = createSampleList();
        Collections.shuffle(list1, new Random(42));
        List<Title> list2 = new ArrayList<>(list1);
        List<Title> list3 = new ArrayList<>(list1);

        // Спосіб 1: анонімний клас Comparator
        list1.sort(new Comparator<Title>() {
            @Override
            public int compare(Title a, Title b) {
                return Integer.compare(a.getYear(), b.getYear());
            }
        });
        System.out.println("1) Анонімний клас:        " + list1);

        // Спосіб 2: лямбда-вираз
        list2.sort((a, b) -> Integer.compare(a.getYear(), b.getYear()));
        System.out.println("2) Лямбда-вираз:          " + list2);

        // Спосіб 3: Comparator.comparingInt з посиланням на метод
        list3.sort(Comparator.comparingInt(Title::getYear));
        System.out.println("3) Comparator.comparing:  " + list3);

        // Спроба змінювати локальну змінну всередині лямбди:
        // int calls = 0;

        System.out.println("
=== Дослід 2: Цикл for-each проти Stream API та лінивість потоків (peek) ===");
        List<Title> titles = createSampleList();
        List<String> byLoop = titlesAfter2015Loop(titles);
        List<String> byStream = titlesAfter2015Stream(titles);
        System.out.println("Результат циклу (після 2015 р.):  " + byLoop);
        System.out.println("Результат потоку (після 2015 р.): " + byStream);

        System.out.println("
Запуск конвеєра з peek БЕЗ термінальної операції:");
        Stream<String> lazyStream = titles.stream()
                .filter(t -> t.getYear() > 2015)
                .peek(t -> System.out.println("  [peek] " + t.getId() + " " + t.getName()))
                .map(Title::getName);
        System.out.println("(Конвеєр створено і збережено у змінну lazyStream - нічого не надруковано!)");

        System.out.println("
Запуск конвеєра з peek З термінальною операцією toList():");
        List<String> evaluated = lazyStream.toList();
        System.out.println("Отриманий список: " + evaluated);

        System.out.println("
=== Дослід 3: Робота з Optional (пошук без результату та з результатом) ===");
        Optional<Title> before1950 = titles.stream()
                .filter(t -> t.getYear() < 1950)
                .findFirst();
        System.out.println("Optional для пошуку тайтлу до 1950 року: " + before1950);

        try {
            Title nonExistent = before1950.get();
            System.out.println(nonExistent);
        } catch (NoSuchElementException e) {
            System.out.println("Виклик get() на порожньому Optional кинув виняток: " + e);
        }

        String safeNotFound = titles.stream()
                .filter(t -> t.getYear() < 1950)
                .findFirst()
                .map(Title::getName)
                .orElse("не знайдено");
        System.out.println("Безпечне отримання через map(...).orElse(...) (до 1950 р.):   " + safeNotFound);

        String safeFound = titles.stream()
                .filter(t -> t.getYear() > 2015)
                .findFirst()
                .map(Title::getName)
                .orElse("не знайдено");
        System.out.println("Безпечне отримання через map(...).orElse(...) (після 2015 р.): " + safeFound);
    }
}
