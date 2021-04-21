import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] linkedArray = (Item[]) new Object[1];
    private int size = 0;
    private int tail = 0;

    public RandomizedQueue() {}

    public boolean isEmpty() { return size==0; }
    public int size() { return size; }

    private int getRandom() {
        int randIdx;
        Item value;
        do {
            randIdx = StdRandom.uniform(0, tail);
            value = linkedArray[randIdx];
        } while(value==null);
        return randIdx;
    }

    private void doubleSize() {
        Item[] temp = (Item[]) new Object[size*2];
        int nonNullCount = 0;
        for (int i = 0; i<tail; i++) {
            if (linkedArray[i]!= null) {
                temp[nonNullCount] = linkedArray[i];
                nonNullCount++;
            }
        }
        linkedArray = temp;
        tail = size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Enter a valid item");
        if (tail == linkedArray.length) doubleSize();
        linkedArray[tail] = item;
        size++;
        tail++;
    }

    public Item dequeue() {
        if (size()==0) throw new java.util.NoSuchElementException("Queue is empty");
        if (!isEmpty() && size < linkedArray.length/4) doubleSize();
        int randIdx = getRandom();
        Item item = linkedArray[randIdx];
        linkedArray[randIdx] = null;
        size--;
        return item;
    }

    public Item sample() {
        if (size()==0) throw new java.util.NoSuchElementException("Queue is empty");
        int randomIndex =getRandom();
        return linkedArray[randomIndex];
    }


    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int n = size;
        public boolean hasNext() {
            return n != 0;
        }
        public Item next () { 
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("Queue is empty");
            }
            int rand = StdRandom.uniform(n);
            Item itemtoreturn = linkedArray[rand];
            if (rand == n - 1) {
                n--;
                return itemtoreturn;
            }
            else {
                linkedArray[rand] = linkedArray[n-1];
                linkedArray[n-1] = itemtoreturn;
                n--;
                return itemtoreturn;
            }
 
        }
        public void remove () {
            throw new UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
        for (int i = 0; i<10; i++) {
            r.enqueue(i);
        }
        Iterator<Integer> iter = r.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }
}
 