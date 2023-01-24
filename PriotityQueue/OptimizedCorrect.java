package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedCorrect<T> implements ExtrinsicMinPQ<T> {
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
    public OptimizedCorrect() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
        items.add(new PriorityNode<>(null, -1.));
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        this.items.add(new PriorityNode<>(item, priority)); // Add an item to the end of list
        int pos = GetSwimPos(size()); //Swim to restore the heap invariant
        itemToIndex.put(item, pos);
    }


    private int GetSwimPos(int index) {
        int parent = parent(index);
        while (accessible(parent) && (items.get(index).priority() < items.get(parent).priority())) {
            this.itemToIndex.replace(items.get(parent).item(), index);
            this.itemToIndex.replace(items.get(index).item(), parent);
            swap(index, parent);
            index = parent;
            parent = parent(index); // needed to start next iteration
        }
        return index;
    }

    private void swap(int index1, int index2) {
        this.itemToIndex.replace(this.items.get(index1).item(), index2);
        this.itemToIndex.replace(this.items.get(index2).item(), index1);

        PriorityNode<T> temp = this.items.get(index1);
        this.items.set(index1, this.items.get(index2));
        this.items.set(index2, temp);

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

    @Override
    public boolean contains(T item) {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        return itemToIndex.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        return this.items.get(1).item(); // constant
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        // 1st: swap the root and the last
        T min = peekMin(); // constant
        swap(1, size()); // constant

        // 2nd: remove the last
        this.items.remove(size()); // constant
        this.itemToIndex.remove(min); // constant

        // 3rd: sink the root to its proper position
        GetSinkPos(1); // log N

        return min;

    }

    private int GetSinkPos(int index) {
        int child = min(left(index), right(index));
        while (accessible(child) && (items.get(index).priority() > items.get(child).priority())) {
            this.itemToIndex.replace(items.get(child).item(), index);
            this.itemToIndex.replace(items.get(index).item(), child);
            swap(index, child);
            index = child;
            child = min(left(index), right(index));
        }
        return index;
    }

    private int min(int index1, int index2) {
        if (!accessible(index1) && !accessible(index2)) {
            return 0;
        } else if (accessible(index1) && (!accessible(index2)
                || (items.get(index1).priority() < items.get(index2).priority()))) {
            return index1;
        } else {
            return index2;
        }
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
//        int item_Index = itemToIndex.get(item);
//        swap(item_Index, size());
//
//        // 2nd: remove the last
//        this.items.remove(size());
//        this.itemToIndex.remove(item);
//
//        // 3rd: sink the root to its proper position
//        GetSinkPos(item_Index);
//
//        add(item, priority);

        int item_Index = itemToIndex.get(item); // constant
        this.items.get(item_Index).setPriority(priority); // constant
        int new_index = GetSinkPos(item_Index); // log N
        if (new_index == item_Index) {
            new_index = GetSwimPos(item_Index);
        }
        this.itemToIndex.replace(this.items.get(new_index).item(), new_index); // constant

//        add(item, priority);
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        return this.items.size() - 1;
    }
}

