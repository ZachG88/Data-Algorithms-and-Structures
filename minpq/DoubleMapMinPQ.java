package minpq;

import java.util.*;

/**
 * {@link TreeMap} and {@link HashMap} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class DoubleMapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link NavigableMap} of priority values to all items that share the same priority values.
     */
    private final NavigableMap<Double, Set<T>> priorityToItem;
    /**
     * {@link Map} of items to their associated priority values.
     */
    private final Map<T, Double> itemToPriority;


    /**
     * Main method
     */
    public static void main(String[] args) {
        ExtrinsicMinPQ<String> pq = new DoubleMapMinPQ<>();
        pq.add("1", 1.0);
        pq.add("2", 2.0);
        pq.add("3", 3.0);
        pq.add("4", 4.0);
        pq.add("5", 5.0);
        pq.add("6", 6.0);

        // Call methods to evaluate behavior.
        pq.changePriority("3", 0.0);
        pq.changePriority("1", 7.0);
        while (!pq.isEmpty()) {
            System.out.println(pq.removeMin());
        }
    }

    /**
     * Constructs an empty instance.
     */
    public DoubleMapMinPQ() {
        priorityToItem = new TreeMap<>();
        itemToPriority = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        if (!priorityToItem.containsKey(priority)) {
            priorityToItem.put(priority, new HashSet<>());
        }
        Set<T> itemsWithPriority = priorityToItem.get(priority);
        itemsWithPriority.add(item);
        itemToPriority.put(item, priority);
    }

    @Override
    public boolean contains(T item) {
        return itemToPriority.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double minPriority = priorityToItem.firstKey();
        Set<T> itemsWithMinPriority = priorityToItem.get(minPriority);
        return firstOf(itemsWithMinPriority);
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double minPriority = priorityToItem.firstKey();
        Set<T> itemsWithMinPriority = priorityToItem.get(minPriority);
        T item = firstOf(itemsWithMinPriority);
        itemsWithMinPriority.remove(item);
        if (itemsWithMinPriority.isEmpty()) {
            priorityToItem.remove(minPriority);
        }
        itemToPriority.remove(item);
        return item;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        double oldPriority = itemToPriority.get(item);
        if (priority != oldPriority) {
            Set<T> itemsWithOldPriority = priorityToItem.get(oldPriority);
            itemsWithOldPriority.remove(item);
            if (itemsWithOldPriority.isEmpty()) {
                priorityToItem.remove(oldPriority);
            }
            itemToPriority.remove(item);
            add(item, priority);
        }
    }

    @Override
    public int size() {
        return itemToPriority.size();
    }

    /**
     * Returns any one element from the given iterable.
     *
     * @param it the iterable of elements.
     * @return any one element from the given iterable.
     */
    private T firstOf(Iterable<T> it) {
        return it.iterator().next();
    }
}
