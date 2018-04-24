import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class FastCollinearPoints {
    
    private int segCount = 0;
    private Point[] origins;
    private Point[] sorted;
    private LineSegment[] segments = new LineSegment[1];
    
    public FastCollinearPoints(Point[] points) {
        
        if (points == null) { throw new IllegalArgumentException(); }
        
        // Create two copies of the array
        origins = new Point[points.length];
        sorted = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) { throw new IllegalArgumentException(); }
            origins[i] = points[i];
            sorted[i] = points[i];
        }
        
        // Check for repeated points
        Arrays.sort(sorted, 0, sorted.length);
        for (int i = 0; i < sorted.length - 1; i++) {
            if (sorted[i].compareTo(sorted[i+1]) == 0) { throw new IllegalArgumentException(); }
        }

        for (int i = 0; i < origins.length; i++) {
            // Sort the points with respect to the compareTo and slopeOrder() comparators
            Arrays.sort(sorted, 0, sorted.length);
            Arrays.sort(sorted, 0, sorted.length, origins[i].slopeOrder());
            double refSlope = origins[i].slopeTo(sorted[0]);
            int count = 2;
            boolean omitSegment = false;
            // Check for any adjacent equal slopes along the newly sorted array
            for (int j = 1; j < sorted.length; j++) {
                double currentSlope = origins[i].slopeTo(sorted[j]);
                if (refSlope == currentSlope) {
                    count++;
                    // Turn a flag on to omit any non-maximal segments of >= 4 points (flag detects if the origin 
                    // is not on the edge of the line segment).
                    if (origins[i].compareTo(sorted[j]) != -1) {
                        omitSegment = true;
                    }
                    // Create a line segment once a maximal segment of >= 4 points has been detected (corner case)
                    if (count >= 4 && !omitSegment && j == sorted.length - 1) {
                        if (segCount >= segments.length) { resizeArray(); }
                        segments[segCount] = new LineSegment(origins[i], sorted[j]);
                        segCount++;
                    }
                }
                else {
                    // Create a line segment once a maximal segment of >= 4 points has been detected (non-corner case)
                    if (count >= 4 && !omitSegment) {
                        if (segCount >= segments.length) { resizeArray(); }
                        segments[segCount] = new LineSegment(origins[i], sorted[j-1]);
                        segCount++;
                    }
                    refSlope = currentSlope;
                    count = 2;
                    // Turn the "omit" flag off when a new slope has been detected
                    if (origins[i].compareTo(sorted[j]) == -1) {
                        omitSegment = false;
                    }
                    // Turn the "omit" flag on to omit any non-maximal segment of >= 4 points
                    else {
                        omitSegment = true;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if (segment != null) {
                StdOut.println(segment);
                segment.draw();
            }
        }
        StdDraw.show();
    }
    
}
    
    