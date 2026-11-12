package ua.duikt.holoshchuk.pr6;

import ua.duikt.holoshchuk.pr6.TitleVariants.TitleNoEquals;
import ua.duikt.holoshchuk.pr6.TitleVariants.TitleOnlyEquals;
import ua.duikt.holoshchuk.pr6.TitleVariants.TitleProper;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    public static <E> E getFirstElement(List<E> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number n : numbers) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("=== Дослід 1: Generics проти Object та коваріантність списків ===");
        Box<TitleProper> titleBox = new Box<>();
        titleBox.put(new TitleProper("T-1017", "Назад у майбутнє", 1985));
        // titleBox.put("Рядок замість Title");

        TitleProper fromGeneric = titleBox.get();
        System.out.println("Отримано з Box<TitleProper> без приведення типів: " + fromGeneric);

        ObjectBox rawBox = new ObjectBox();
        rawBox.put("Рядок замість Title");
        try {
            TitleProper wrongCast = (TitleProper) rawBox.get();
            System.out.println(wrongCast);
        } catch (ClassCastException e) {
            System.out.println("Помилка виконання в ObjectBox: " + e);
        }

        List<Integer> ints = Arrays.asList(10, 20, 30);
        List<Number> nums = Arrays.asList(1.5, 2.5, 6.0);
        System.out.println("Сума List<Integer> через List<? extends Number>: " + sumNumbers(ints));
        System.out.println("Сума List<Number> через List<? extends Number>: " + sumNumbers(nums));

        System.out.println("
=== Дослід 2: Продуктивність ArrayList, LinkedList та HashSet (100 000 елементів) ===");
        int totalElements = 100_000;
        for (int run = 1; run <= 3; run++) {
            List<TitleProper> arrayList = new ArrayList<>(totalElements);
            List<TitleProper> linkedList = new LinkedList<>();
            Set<TitleProper> hashSet = new HashSet<>(totalElements * 2);

            for (int i = 0; i < totalElements; i++) {
                TitleProper t = new TitleProper("T-" + i, "Тайтл " + i, 2000 + (i % 25));
                arrayList.add(t);
                linkedList.add(t);
                hashSet.add(t);
            }

            long t0 = System.nanoTime();
            for (int i = 0; i < 1000; i++) {
                arrayList.add(arrayList.size() / 2, new TitleProper("INS-A-" + i, "Вставка", 2026));
            }
            long arrayInsertMs = (System.nanoTime() - t0) / 1_000_000;

            t0 = System.nanoTime();
            for (int i = 0; i < 1000; i++) {
                linkedList.add(linkedList.size() / 2, new TitleProper("INS-L-" + i, "Вставка", 2026));
            }
            long linkedInsertMs = (System.nanoTime() - t0) / 1_000_000;

            t0 = System.nanoTime();
            for (int i = 0; i < 1000; i++) {
                arrayList.contains(new TitleProper("T-" + (totalElements - 10), "Пошук", 2020));
            }
            long arrayContainsMs = (System.nanoTime() - t0) / 1_000_000;

            t0 = System.nanoTime();
            for (int i = 0; i < 1000; i++) {
                linkedList.contains(new TitleProper("T-" + (totalElements - 10), "Пошук", 2020));
            }
            long linkedContainsMs = (System.nanoTime() - t0) / 1_000_000;

            t0 = System.nanoTime();
            for (int i = 0; i < 1000; i++) {
                hashSet.contains(new TitleProper("T-" + (totalElements - 10), "Пошук", 2020));
            }
            long hashContainsMs = (System.nanoTime() - t0) / 1_000_000;

            System.out.printf("Замір #%d | Вставка x1000 у середину: ArrayList=%d мс, LinkedList=%d мс | contains x1000: ArrayList=%d мс, LinkedList=%d мс, HashSet=%d мс%n",
                    run, arrayInsertMs, linkedInsertMs, arrayContainsMs, linkedContainsMs, hashContainsMs);
        }

        // Видалення у циклі for-each vs Iterator
        List<TitleProper> sampleList = new ArrayList<>();
        sampleList.add(new TitleProper("T-1", "Матриця", 1999));
        sampleList.add(new TitleProper("T-2", "Дюна", 2021));
        sampleList.add(new TitleProper("T-3", "Шрек", 2001));

        try {
            for (TitleProper t : sampleList) {
                if (t.getYear() < 2010) {
                    sampleList.remove(t);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Видалення прямо в циклі for-each викликало виняток: " + e);
        }

        Iterator<TitleProper> it = sampleList.iterator();
        while (it.hasNext()) {
            TitleProper t = it.next();
            if (t.getYear() < 2010) {
                it.remove();
            }
        }
        System.out.println("Після безпечного видалення через Iterator залишилось елементів: " + sampleList.size() + " -> " + sampleList);

        System.out.println("
=== Дослід 3: Поведінка HashSet залежно від equals та hashCode ===");
        Set<TitleNoEquals> setNoEq = new HashSet<>();
        setNoEq.add(new TitleNoEquals("T-100", "Аркейн", 2021));
        setNoEq.add(new TitleNoEquals("T-100", "Аркейн (дубль)", 2021));
        System.out.println("1) Без equals і hashCode: size = " + setNoEq.size()
                + ", contains = " + setNoEq.contains(new TitleNoEquals("T-100", "Аркейн", 2021)));

        Set<TitleOnlyEquals> setOnlyEq = new HashSet<>();
        setOnlyEq.add(new TitleOnlyEquals("T-100", "Аркейн", 2021));
        setOnlyEq.add(new TitleOnlyEquals("T-100", "Аркейн (дубль)", 2021));
        System.out.println("2) Тільки equals (без hashCode): size = " + setOnlyEq.size()
                + ", contains = " + setOnlyEq.contains(new TitleOnlyEquals("T-100", "Аркейн", 2021)));

        Set<TitleProper> setProper = new HashSet<>();
        TitleProper first = new TitleProper("T-100", "Аркейн", 2021);
        setProper.add(first);
        setProper.add(new TitleProper("T-100", "Аркейн (дубль)", 2021));
        System.out.println("3) Перевизначено і equals, і hashCode: size = " + setProper.size()
                + ", contains = " + setProper.contains(new TitleProper("T-100", "Аркейн", 2021)));

        // Зміна поля ключа в об'єкта, що вже лежить у HashSet
        first.setId("T-999");
        System.out.println("4) Після мутації ключа first.setId("T-999") всередині HashSet:");
        System.out.println("   contains за старим ключем T-100: " + setProper.contains(new TitleProper("T-100", "Аркейн", 2021)));
        System.out.println("   contains за новим ключем T-999:  " + setProper.contains(new TitleProper("T-999", "Аркейн", 2021)));
    }
}
