package ua.duikt.holoshchuk.pr9;

import java.util.ArrayList;
import java.util.List;

public class ViewCounterRaceDemo {
    static class UnsafeViewCounter {
        private int views = 0;
        public void add() {
            views++;
        }
        public int getViews() {
            return views;
        }
    }

    static class SafeViewCounter {
        private int views = 0;
        public synchronized void add() {
            views++;
        }
        public synchronized int getViews() {
            return views;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int threadsCount = 4;
        final int incrementsPerThread = 100_000;
        final int expected = threadsCount * incrementsPerThread;

        for (int run = 1; run <= 3; run++) {
            UnsafeViewCounter counter = new UnsafeViewCounter();
            List<Thread> threads = new ArrayList<>();
            for (int t = 0; t < threadsCount; t++) {
                Thread th = new Thread(() -> {
                    for (int i = 0; i < incrementsPerThread; i++) {
                        counter.add();
                    }
                });
                threads.add(th);
                th.start();
            }
            for (Thread th : threads) {
                th.join();
            }
            System.out.printf("Без синхронізації (запуск %d): %d / %d%n", run, counter.getViews(), expected);
        }

        for (int run = 1; run <= 3; run++) {
            SafeViewCounter counter = new SafeViewCounter();
            List<Thread> threads = new ArrayList<>();
            for (int t = 0; t < threadsCount; t++) {
                Thread th = new Thread(() -> {
                    for (int i = 0; i < incrementsPerThread; i++) {
                        counter.add();
                    }
                });
                threads.add(th);
                th.start();
            }
            for (Thread th : threads) {
                th.join();
            }
            System.out.printf("synchronized (запуск %d):      %d / %d%n", run, counter.getViews(), expected);
        }
    }
}
