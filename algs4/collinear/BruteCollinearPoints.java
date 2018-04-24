import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class BruteCollinearPoints {
    
    private int segCount = 0;
    private Point[] sorted;
    private LineSegment[] segments = new LineSegment[1];
    
    public BruteCollinearPoints(Point[] points) {
        
        if (points == null) { throw new IllegalArgumentException(); }
        
        // Create two copies of the array and check for corner cases
        sorted = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) { throw new IllegalArgumentException(); }
            sorted[i] = points[i];
        }
        Arrays.sort(sorted, 0, sorted.length);
        for (int i = 0; i < points.length - 1; i++) {
            if (sorted[i].compareTo(sorted[i+1]) == 0) { throw new IllegalArgumentException(); }
        }
        
        // Brute force algorithm to check for 4 any collinear-points segments on a given set
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == -1) {
                    double segmentA = points[i].slopeTo(points[j]);
                    for (int k = 0; k < points.length; k++) {
                        if (points[j].compareTo(points[k]) == -1) {
                            double segmentB = points[j].slopeTo(points[k]);
                            for (int l = 0; l < points.length; l++) {
                                if (points[k].compareTo(points[l]) == -1) {
                                    double segmentC = points[k].slopeTo(points[l]);
                                    if (segmentA == segmentB && segmentB == segmentC) {
                                        if (segCount >= segments.length) { resizeArray(); }
                                        segments[segCount] = new LineSegment(points[i], points[l]);
                                        segCount++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public int numberOfSegments() {
        return segCount;
    }
    
    public LineSegment[] segments() {
        LineSegment[] finalsegments = new LineSegment[segCount];
        for (int i = 0; i < segCount; i++) {
            finalsegments[i] = segments[i];
        }
        return finalsegments;
    }
    
    private void resizeArray() {
        LineSegment[] temp = new LineSegment[(segments.length * 2)];
        for (int i = 0; i < segCount; i++) {
            temp[i] = segments[i];
        }
        segments = temp;
    }
    
    public static void main(String args[]) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
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
    
    