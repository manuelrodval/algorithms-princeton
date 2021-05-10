import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> tree = new TreeSet<Point2D>();

    public PointSET() {
        // construct an empty set of points
    }

    public boolean isEmpty() {
        // is the set empty?
        return tree.isEmpty();
    }

    public int size() {
        // number of points in the set
        return tree.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("Argument can't be null");
        if (tree.contains(p)) return;
        tree.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) throw new IllegalArgumentException("Argument can't be null");
        return tree.contains(p);
    }

    public void draw() {
        // draw all points to standard draw
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D p: tree) {
            p.draw();
        }
        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException("Argument can't be null");
        Queue<Point2D> inSquare = new Queue<Point2D>();
        for (Point2D p: tree) {
            if (rect.contains(p)) inSquare.enqueue(p);

        }
        return inSquare;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException("Argument can't be null");
        if (tree.isEmpty()) return null;
        Point2D near = new Point2D(p.x(), p.y());
        double dist = 2.0;
        for (Point2D n: tree) {
            if (p.distanceSquaredTo(n) < dist) {
                dist = p.distanceSquaredTo(n);
                near = new Point2D(n.x(), n.y());
            }
        }
        return near;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
        PointSET set = new PointSET();
        for (int i = 0; i < 200 ; i ++) {
            Point2D p = new Point2D(StdRandom.uniform(), StdRandom.uniform());
            set.insert(p);
        }
        StdOut.println(set.isEmpty());
        StdOut.println(set.size());
        set.draw();
        RectHV rect = new RectHV(0.4, 0.4, 0.8, 0.8);
        for (Point2D s: set.range(rect)){
            StdOut.println(s);
        }
        StdOut.println(set.nearest(new Point2D(0,0)));
    }
 }