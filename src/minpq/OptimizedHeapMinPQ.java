package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /**
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    static final int START_INDEX = 1;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
        elements.add(null);
    }

    private void swap(int a, int b) {
        PriorityNode<E> temp = elements.get(a);
        elements.set(a, elements.get(b));
        elements.set(b, temp);
        elementsToIndex.put(elements.get(a).element(), a);
        elementsToIndex.put(elements.get(b).element(), b);
    }

    private void swim(int curr) {
        while (curr > 1 && elements.get(curr).priority() < elements.get(curr / 2).priority()) {
            swap(curr, curr / 2);
            curr /= 2;
        }
    }

    private void sink(int curr) {
        for (int i = 2 * curr; i <= size(); i *= 2) {
            if (i + 1 <= size() && elements.get(i).priority() > elements.get(i + 1).priority()) {
                i++;
            }
            if (elements.get(curr).priority() > elements.get(i).priority()) {
                swap(curr, i);
                curr = i;
            } else {
                break;
            }
        }
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        elements.add(new PriorityNode<>(element, priority));
        int index = size();
        elementsToIndex.put(elements.get(index).element(), index);
        swim(index);
    }

    @Override
    public boolean contains(E element) {
        return elementsToIndex.containsKey(element);
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return elements.get(START_INDEX).element();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        E result = elements.get(START_INDEX).element();
        elementsToIndex.remove(result);
        if (size() > START_INDEX) {
            elements.set(START_INDEX, elements.get(size()));
            elementsToIndex.put(elements.get(START_INDEX).element(), START_INDEX);
        }
        elements.remove(size());
        sink(START_INDEX);

        return result;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        int index = elementsToIndex.get(element);
        elements.get(index).setPriority(priority);
        swim(index);
        sink(index);
    }

    @Override
    public int size() {
        return elements.size() - 1;
    }
}
