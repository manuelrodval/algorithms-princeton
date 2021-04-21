import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first = null; // Last added in Deque
    private Node last = null; // Most recently added in Queue
    private int N = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public Deque() {}

    public boolean isEmpty() { return N == 0; }
    public int size() { return N; }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Enter a valid item");
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.next = null;
            last = first;
            N++;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            oldFirst.next = first;
            first.previous = oldFirst;
            N++;
        }
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Enter a valid item");
        if (isEmpty()) {
            addFirst(item);
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = oldLast;
            oldLast.previous = last;
            N++;
        }
    }

    public Item removeFirst() { 
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque is empty");
        Item item = first.item;
        first = first.previous;
        N--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque is empty");
        Item item = last.item;
        last = last.next;
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {return current != null;}
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported");
        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("Deque is empty");
            Item item = current.item;
            current = current.previous;
            return item;
        }
    }

    // Unit Testing
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        int counter = 0;
        while (counter < 20) {
            if (counter % 2 == 0) d.addFirst(counter);
            else d.addLast(counter);
            counter++;
        }

        Iterator<Integer> iter = d.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }

        counter = 0;
        while (!d.isEmpty()) {
            if (d.size() % 2 == 0) StdOut.println("primero: " + d.removeFirst());
            else StdOut.println("ultimo: " + d.removeLast());
            counter++;
        }
    } 
}
