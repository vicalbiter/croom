import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import java.lang.IllegalArgumentException;

public class PointSET {
    
    private SET<Point2D> set;
    
    public PointSET() {
        set = new SET<Point2D>();
    }
    
    // Return whether if the set is empty
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    // Return the number of points in the set
    public int size() {
        return set.size(); 
    }
    
    // Add point to the set (if it's not already in the set)
    public void insert(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        set.add(p);
    }
    
    // Return whether if the set contains point p
    public boolean contains(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        return set.contains(p);
    }
    
    // Draw all points to standard draw
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1.0);
        StdDraw.setYscale(0, 1.0);
        for (Point2D p : set) {
            p.draw();
        }
        StdDraw.show();
    }
    
    // Return all points inside a query rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new IllegalArgumentException(); }
        Stack<Point2D> points = new Stack<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                points.push(p);
            }
        }
        return points;
    }
    
    // Return the nearest neighbor of point p in the set
    public Point2D nearest(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        if (isEmpty()) { return null; }
        Point2D nearest = set.min();
        double ndistance = p.distanceTo(nearest);
        for (Point2D neighbor : set) {
            if (p.distanceTo(neighbor) < ndistance) {
                nearest = neighbor;
                ndistance = p.distanceTo(nearest);
            }
        }
        return nearest;
    }
    
    public static void main(String[] args) {
        
    }
}
