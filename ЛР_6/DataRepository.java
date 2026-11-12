package ua.duikt.holoshchuk.lr6.storage;

import java.util.*;

public class DataRepository<K, T extends Identifiable<K>> implements Iterable<T> {
    private final Map<K, T> recordsByKey = new LinkedHashMap<>();

    public boolean add(T record) {
        if (record == null || record.getKey() == null) {
            throw new IllegalArgumentException("Запис та його ключ не можуть бути null");
        }
        if (recordsByKey.containsKey(record.getKey())) {
            return false;
        }
        recordsByKey.put(record.getKey(), record);
        return true;
    }

    public T findByKey(K key) {
        return recordsByKey.get(key);
    }

    public int size() {
        return recordsByKey.size();
    }

    public List<T> getSorted(Comparator<T> comparator) {
        List<T> copy = new ArrayList<>(recordsByKey.values());
        Collections.sort(copy, comparator);
        return Collections.unmodifiableList(copy);
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableCollection(recordsByKey.values()).iterator();
    }
}
