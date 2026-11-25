package ua.duikt.holoshchuk.pr9;

public class PlaylistDeadlockDemo {
    static class Playlist {
        private final int id;
        private final String name;
        private int trackCount;

        public Playlist(int id, String name, int trackCount) {
            this.id = id;
            this.name = name;
            this.trackCount = trackCount;
        }

        public int getId() { return id; }
        public int getTrackCount() { return trackCount; }

        // Варіант з взаємним блокуванням (для зняття дампу jstack)
        public static void transferDeadlock(Playlist from, Playlist to) {
            synchronized (from) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (to) {
                    if (from.trackCount > 0) {
                        from.trackCount--;
                        to.trackCount++;
                    }
                }
            }
        }

        // Виправлений варіант: захоплення моніторів строго за зростанням id
        public static void transferOrdered(Playlist from, Playlist to) {
            if (from.id == to.id) return;
            Playlist firstLock = from.id < to.id ? from : to;
            Playlist secondLock = from.id < to.id ? to : from;

            synchronized (firstLock) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (secondLock) {
                    if (from.trackCount > 0) {
                        from.trackCount--;
                        to.trackCount++;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Playlist p1 = new Playlist(1, "Favorites", 50);
        Playlist p2 = new Playlist(2, "ChillMix", 50);

        boolean runFixed = args.length > 0 && args[0].equals("--fixed");

        Thread t0 = new Thread(() -> {
            if (runFixed) Playlist.transferOrdered(p1, p2);
            else Playlist.transferDeadlock(p1, p2);
        }, "Thread-0");

        Thread t1 = new Thread(() -> {
            if (runFixed) Playlist.transferOrdered(p2, p1);
            else Playlist.transferDeadlock(p2, p1);
        }, "Thread-1");

        t0.start();
        t1.start();
        t0.join();
        t1.join();

        System.out.printf("Успішно завершено! p1=%d треків, p2=%d треків%n",
                p1.getTrackCount(), p2.getTrackCount());
    }
}
