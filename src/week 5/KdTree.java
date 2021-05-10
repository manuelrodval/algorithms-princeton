import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {

    private Node root;
    private int N;

    public KdTree() {
        // construct an empty set of points
        this.root = null;
        this.N = 0;
    }

    private class Node {
        private final Point2D point;
        private Node left, right;
        private final RectHV frame;

        public Node(Point2D point, RectHV frame) {
            this.point = point;
            this.left = null;
            this.right = null;
            this.frame = frame;
        }
    }

    public boolean isEmpty() {
        // is the set empty?
        return size() == 0;
    }

    public int size() {
        // number of points in the set
        return N;
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("Argument can't be null");
        if (!contains(p)) {root = insert(root, null, p, true);}
    }

    private Node insert(Node n, Node prev, Point2D p, boolean dir) {
        if (n == null) {
            RectHV rect;
            if (prev == null) rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            else {
                if (!dir) {
                    if (prev.point.x() > p.x()) rect = new RectHV(prev.frame.xmin(), prev.frame.ymin(),  prev.point.x(), prev.frame.ymax());
                    else rect = new RectHV(prev.point.x(), prev.frame.ymin(), prev.frame.xmax(), prev.frame.ymax());
                } else {
                    if (prev.point.y() > p.y()) rect = new RectHV(prev.frame.xmin(),prev.frame.ymin(), prev.frame.xmax(), prev.point.y());
                    else rect = new RectHV(prev.frame.xmin(), prev.point.y(), prev.frame.xmax(), prev.frame.ymax());
                }
            }            
            N++;
            return new Node(p, rect);
        }
        double cmp;
        if (dir) cmp = n.point.x() - p.x();
        else cmp = n.point.y() - p.y();
        if (cmp > 0) n.left = insert(n.left, n, p, !dir);
        else n.right = insert(n.right, n, p, !dir);    
        return n;
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) throw new IllegalArgumentException("Argument can't be null");
        return contains(root, p, true);
    }

    private boolean contains(Node n, Point2D p, boolean dir) {
        if (n == null) return false;
        if (n.point.equals(p)) return true;
        double cmp;
        if (dir) cmp = n.point.x() - p.x();
        else cmp = n.point.y() - p.y();
        if (cmp > 0) return contains(n.left, p,!dir);
        else return contains(n.right, p,!dir);    
    }

    public void draw() {
        // draw all points to standard draw
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);
        draw(root, 0);
        StdDraw.show();
    }

    private void draw(Node x, int level) {
        if (x!=null) {
            draw(x.left, level + 1);
            if (level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.point.x(), x.frame.ymin(), x.point.x(), x.frame.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.frame.xmin(), x.point.y(), x.frame.xmax(), x.point.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(x.point.x(), x.point.y());

            draw(x.right, level + 1);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException("Argument can't be null");
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }

    private void range(Node n, RectHV rect, Queue<Point2D> q) {
        if (n == null) return;
        if (n.frame.intersects(rect)) {
            if (rect.contains(n.point)) q.enqueue(n.point);
            range(n.left, rect, q);
            range(n.right, rect, q);
        }
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException("Argument can't be null");
        if (isEmpty()) return null;
        Point2D closest = null;
        closest = nearest(root, p, closest);
        return closest;
    }

    private Point2D nearest(Node n, Point2D p, Point2D closest) {
        if (n != null) {
            double dist1 = n.frame.distanceSquaredTo(p);
            double dist2 = 5.0;
            if (closest != null) dist2 = p.distanceSquaredTo(closest);

            if (dist1 < dist2) {
                closest = nearest(n.left, p, closest);
                if (closest == null) closest = n.point;
                else {
                    if (p.distanceSquaredTo(n.point) < p.distanceSquaredTo(closest)) closest = n.point;
                }
                closest = nearest(n.right, p, closest);
            }
        }
        return closest;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
        KdTree set = new KdTree();
        for (int i = 0; i < 10 ; i ++) {
            Point2D p = new Point2D(StdRandom.uniform(), StdRandom.uniform());
            set.insert(p);
        }

        StdOut.println(set.isEmpty());
        StdOut.println(set.size());
        set.draw();
        RectHV rect = new RectHV(0.4, 0.4, 0.8, 0.8);
        StdOut.println("Range Test : ---------------");
        for (Point2D s: set.range(rect)){
            StdOut.println(s);
        }
        StdOut.println("----------------------------");
        StdOut.println("nearest point: " + set.nearest(new Point2D(0.354, 0.017)));
    }
}



