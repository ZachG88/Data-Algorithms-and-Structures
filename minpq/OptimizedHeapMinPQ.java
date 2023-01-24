package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
        items.add(new PriorityNode<>(null, -1.));
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        this.items.add(new PriorityNode<>(item, priority));
        int position = GetSwimPos(size());
        itemToIndex.put(item, position);
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    public int GetSwimPos(int index) {
        int parentNode = parent(index);
        while (accessible(parentNode) && (items.get(index).priority() < items.get(parentNode).priority())) {
            this.itemToIndex.replace(items.get(parentNode).item(), index);
            this.itemToIndex.replace(items.get(index).item(), parentNode);
            swap(index, parentNode);
            index = parentNode;
            parentNode = parent(index); // needed to start next iteration
        }
        return index;
    }

    private static int parent(int index) {
        return index / 2;
    }

    private static int left(int index) {
        return index * 2;
    }

    private static int right(int index) {
        return left(index) + 1;
    }

    private boolean accessible(int index) {
        return 1 <= index && index <= size();
    }

    private void swap(int index1, int index2) {
        this.itemToIndex.replace(this.items.get(index1).item(), index2);
        this.itemToIndex.replace(this.items.get(index2).item(), index1);

        PriorityNode<T> temp = this.items.get(index1);
        this.items.set(index1, this.items.get(index2));
        this.items.set(index2, temp);
    }

    @Override
    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return this.items.get(1).item();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        T min = peekMin();
        swap(1, size());
        this.items.remove(size()); // constant
        this.itemToIndex.remove(min); //constant
        GetSinkPos(1);

        return min;
        //throw new UnsupportedOperationException("Not implemented yet");
    }


    private int GetSinkPos(int index) {
        int child = min(left(index), right(index));
        while(accessible(child) && (items.get(index).priority() > items.get(child).priority())) {
            this.itemToIndex.replace(items.get(child).item(), index);
            this.itemToIndex.replace(items.get(index).item(), child);
            swap(index, child);
            index = child;
            child = min(left(index), right(index));
        }
        return index;
    }

    private int min(int index1, int index2) {
        if(!accessible(index1) && !accessible(index2)) {
            return 0;
        }
        else if (accessible(index1) && (!accessible(index2)
            || (items.get(index1).priority() < items.get(index2).priority()))) {
            return index1;
        }
        else {
            return index2;
        }
    }


    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int oldIndex = itemToIndex.get(item); // constant time
        this.items.get(oldIndex).setPriority(priority); //constant time
        int newIndex = GetSinkPos(oldIndex); //logN time
        if(newIndex == oldIndex) {
            newIndex = GetSwimPos(oldIndex);
        }
        this.itemToIndex.replace(this.items.get(newIndex).item(), newIndex);
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int size() {
        return this.items.size() - 1;
        //throw new UnsupportedOperationException("Not implemented yet");
    }
}
