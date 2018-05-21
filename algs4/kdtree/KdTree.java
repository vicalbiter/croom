import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    
    public KdTree() {
    
    }
    
    // Return whether if the set is empty
    public boolean isEmpty() {
        return false;
    }
    
    // Return the number of points in the set
    public int size() {
        return 0; 
    }
    
    // Add point to the set (if it's not already in the set)
    public void insert(Point2D p) {
        
    }
    
    // Return whether if the set contains point p
    public boolean contains(Point2D p) {
        return false;
    }
    
    // Draw all points to standard draw
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1.0);
        StdDraw.setYscale(0, 1.0);
        //for (Point2D p : set) {
        //    p.draw();
        //}
        StdDraw.show();
    }
    
    // Return all points inside a query rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }
    
    // Return the nearest neighbor of point p in the set
    public Point2D nearest(Point2D p) {
        return null;
    }
    
    public static void main(String[] args) {
        
    }
}