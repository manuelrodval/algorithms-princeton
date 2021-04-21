import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> segmentArray = new ArrayList<>(); // Result array

    public FastCollinearPoints(Point[] points){
        // Corner cases
        if (points == null) throw new IllegalArgumentException("Input can't be null");
        for (int i = 0; i < points.length; i++) { 
            if (points[i] == null) throw new IllegalArgumentException("Point can't be null");
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Found duplicated point");
            }
        }

        // Sorting 
        Point[] pointsCopy = points.clone(); 
        for (int i = 0; i < pointsCopy.length - 3; i++) {
            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());
            for (int p = 0, first = 1, last = 2; last < pointsCopy.length; last++) {
                while (last < pointsCopy.length
                        && Double.compare(pointsCopy[p].slopeTo(pointsCopy[first]), pointsCopy[p].slopeTo(pointsCopy[last])) == 0) {
                    last++;
                }
                if (last - first >= 3 && pointsCopy[p].compareTo(pointsCopy[first]) < 0) {
                    segmentArray.add(new LineSegment(pointsCopy[p], pointsCopy[last - 1]));
                }
                first = last;
            }
        }
    }
    
    

    public int numberOfSegments() {
        return segmentArray.size();
    }

    public LineSegment[] segments() {
        return segmentArray.toArray(new LineSegment[segmentArray.size()]);
    }

    public static void main(String[] args) {
        // Generate Random points
        int x, y;
        Point p;
        Point[] points = new Point[100];
        for (int i = 0; i < points.length; i++) {
            x = StdRandom.uniform(0, 32768);
            y = StdRandom.uniform(0, 32768);
            p = new Point(x, y);
            points[i] = p;
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.005);
        for (Point t : points) {
            t.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
