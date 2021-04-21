import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segmentArray = new ArrayList<>();

    // Finds all line segments containingn 4 points 
    public BruteCollinearPoints(Point[] points) {
        // Corner cases
        if (points == null) throw new IllegalArgumentException("Input can't be null");
        for (int i = 0; i < points.length; i++) { 
            if (points[i] == null) throw new IllegalArgumentException("Point can't be null");
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Found duplicated point");
            }
        }

        // Slopes    
        double s1, s2, s3;
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        // Combination of Slopes
        for (int i = 0; i < pointsCopy.length; i++) {
            for (int j = i + 1; j < pointsCopy.length; j++) {
                s1 = pointsCopy[i].slopeTo(pointsCopy[j]);
                for (int k = j + 1; k < pointsCopy.length; k++) {
                    s2 = pointsCopy[i].slopeTo(pointsCopy[k]);
                    if (s1 == s2) {
                        for (int m = k + 1; m < pointsCopy.length; m++) {
                            s3 = pointsCopy[i].slopeTo(pointsCopy[m]);
                            if (s2 == s3) {
                                LineSegment s = new LineSegment(pointsCopy[i], pointsCopy[m]);
                                segmentArray.add(s);
                            }
                        }
                    }
                }
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
        Point[] points = new Point[500];
        for (int i = 0; i < points.length; i++) {
            x = StdRandom.uniform(0, 32768);
            y = StdRandom.uniform(0, 32768);
            p = new Point(x, y);
            points[i] = p;
        }
        
        points[10] = new Point(3000, 4000);
        points[0] = new Point(6000, 7000);
        points[200] = new Point(14000, 15000);
        points[333] = new Point(20000, 21000);

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}