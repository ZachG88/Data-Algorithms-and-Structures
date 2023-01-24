package minpq;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * {@link PriorityQueue} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each item-priority pair.
     */
    private final PriorityQueue<PriorityNode<T>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

    /**
     * Log N runtime
     * @param item     the element to add.
     * @param priority the priority value for the item.
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        this.pq.add(new PriorityNode<>(item, priority));
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Linear time
     * @param item element to be checked for containment.
     * @return boolean true if pq contains item
     */
    @Override
    public boolean contains(T item) {
        return(this.pq.contains(new PriorityNode<>(item, 0.0)));
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * LogN time
     * @return item T at peek of pq (lowest priority)
     */
    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return this.pq.peek().item();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Log N time
     * @return T minimum node
     * behavior: removes minimum node
     */
    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return this.pq.poll().item();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     *
     * @param item     the element whose associated priority value should be modified.
     * @param priority the updated priority value.
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        this.pq.remove(new PriorityNode<>(item, 0.0)); //linear time
        this.pq.add(new PriorityNode<>(item, priority)); // log N
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int size() {
        return this.pq.size();
        //throw new UnsupportedOperationException("Not implemented yet");
    }
}
