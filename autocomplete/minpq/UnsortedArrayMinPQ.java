package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        items.add(new PriorityNode<>(item, priority));
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean contains(T item) {
        for (PriorityNode<T> tPriorityNode : this.items) {
            if (tPriorityNode.equals(new PriorityNode<>(item, 0.0))) {
                return true;
            }
        }
        return false;
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double priorityMin = this.items.get(0).priority();
        int indexOfMin = 0;
        for (int i = 0; i < this.items.size(); i++) {
            if(priorityMin > this.items.get(i).priority()) {
                priorityMin = this.items.get(i).priority();
                indexOfMin = i;
            }
        }
        return this.items.get(indexOfMin).item();
       // throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double priorityMin = this.items.get(0).priority();
        int indexOfMin = 0;
        for (int i = 0; i < this.items.size(); i++) {
            if(priorityMin > this.items.get(i).priority()) {
                priorityMin = this.items.get(i).priority();
                indexOfMin = i;
            }
        }

        T minItem = this.items.get(indexOfMin).item();
        items.remove(indexOfMin);
        return minItem;

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        for(PriorityNode<T> currNode : this.items) {
            if(currNode.equals(new PriorityNode<>(item, 0.0))) {
                currNode.setPriority(priority);
                break;
            }
        }
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int size() {
        return items.size();
        //throw new UnsupportedOperationException("Not implemented yet");
    }
}
