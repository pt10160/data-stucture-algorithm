package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class UnsortedArrayMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the element-priority pairs in no specific order.
     */
    private final List<PriorityNode<E>> elements;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        elements = new ArrayList<>();
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        this.elements.add(new PriorityNode<>(element, priority));
    }

    @Override
    public boolean contains(E element) {
       for (PriorityNode<E> thisNode: elements){
           if(thisNode.element() == element){
               return true;
           }
       }
       return false;
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return (E) getMin().element();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode min = getMin();
        elements.remove(min);
        return(E) min.element();
    }

    private PriorityNode getMin(){
        PriorityNode minNode = elements.get(0);
        for (int i = 1; i < elements.size(); i++){
            PriorityNode node = elements.get(i);
            if (node.priority() < minNode.priority()){
                minNode = node;
            }
        }
        return minNode;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        for(PriorityNode<E> thisNode : elements) {
            if (thisNode.element() == element) {
                thisNode.setPriority(priority);
            }
        }
    }

    @Override
    public int size() {
        return elements.size();
    }
}
