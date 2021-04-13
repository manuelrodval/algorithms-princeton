import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> input = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            input.enqueue(StdIn.readString());
        }
        for (int i = 0; i < n; i++) {
            StdOut.println(input.dequeue());
        }
    }
}
