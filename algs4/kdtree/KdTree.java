import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.IllegalArgumentException;

public class KdTree {
    
    private Node root;
    private int size;
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private static final RectHV UNIT = new RectHV(0, 0, 1, 1);
    
    private class Node {
        
        private Point2D p;
        private RectHV r;
        private Node lb;
        private Node rt;
        private boolean axis;
        
        public Node(Point2D p, RectHV r, boolean axis) {
            this.p = p;
            this.r = r;
            this.axis = axis;
        }
    }
    
    // Return whether if the set is empty
    public boolean isEmpty() {
        return root == null;
    }
    
    // Return the number of points in the set
    public int size() {
        return size; 
    }
    
    // Add point to the set (if it's not already in the set)
    public void insert(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        root = insert(root, p, VERTICAL, 0, 0, 1, 1);
    }
    
    // Recursive method to insert a new node into the 2D-tree
    private Node insert(Node x, Point2D p, boolean axis, double xmn, double ymn, double xmx, double ymx) {
        if (x == null) {
            size++;
            return new Node(p, new RectHV(xmn, ymn, xmx, ymx), axis);
        }
        if (!p.equals(x.p)) {
            if (axis == VERTICAL) {
                if (p.x() <= x.p.x()) {
                    x.lb = insert(x.lb, p, HORIZONTAL, x.r.xmin(), x.r.ymin(), x.p.x(), x.r.ymax());
                }
                else if (p.x() > x.p.x()) {
                    x.rt = insert(x.rt, p, HORIZONTAL, x.p.x(), x.r.ymin(), x.r.xmax(), x.r.ymax());
                }
            }
            else if (axis == HORIZONTAL) {
                if (p.y() <= x.p.y()) {
                    x.lb = insert(x.lb, p, VERTICAL, x.r.xmin(), x.r.ymin(), x.r.xmax(), x.p.y());
                }
                else if (p.y() > x.p.y()) {
                    x.rt = insert(x.rt, p, VERTICAL, x.r.xmin(), x.p.y(), x.r.xmax(), x.r.ymax());
                }
            }
        }
        return x;
    }

    // Return whether if the set contains point p
    public boolean contains(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        return contains(root, p);
    }
    
    // Recursive method to check if a node is contained inside the 2D-tree
    private boolean contains(Node x, Point2D p) {
        if (x == null) { return false; }
        if (!p.equals(x.p)) {
            if (x.axis == VERTICAL) {
                if (p.x() <= x.p.x()) {
                    return contains(x.lb, p);
                }
                else if (p.x() > x.p.x()) {
                    return contains(x.rt, p);
                }
            }
            else if (x.axis == HORIZONTAL) {
                if (p.y() <= x.p.y()) {
                    return contains(x.lb, p);
                }
                else if (p.y() > x.p.y()) {
                    return contains(x.rt, p);
                }
            }
        }
        return true;
    }
    
    // Draw all points to standard draw
    public void draw() {
        Queue<Node> points = new Queue<Node>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            points.enqueue(x);
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1.0);
        StdDraw.setYscale(0, 1.0);
        for (Node p : points) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            p.p.draw();
            StdDraw.setPenRadius();
            if (p.axis == HORIZONTAL) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(p.r.xmin(), p.p.y(), p.r.xmax(), p.p.y());
            }
            else if (p.axis == VERTICAL) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(p.p.x(), p.r.ymin(), p.p.x(), p.r.ymax());
            }
        }
        StdDraw.show();
    }
    
    // Return all points inside a query rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new IllegalArgumentException(); }
        Stack<Point2D> points = new Stack<Point2D>();
        points = range(points, root, rect);
        return points;
    }
    
    // Recursive method to find all the points within a given range
    private Stack<Point2D> range(Stack<Point2D> points, Node x, RectHV rect) {
        if (x == null) { return points; }
        if (rect.intersects(x.r)) {
            if (rect.contains(x.p)) {
                points.push(x.p);
            }
            points = range(points, x.lb, rect);
            points = range(points, x.rt, rect);
        }
        return points;
    }
    
    // Return the nearest neighbor of point p in the set
    public Point2D nearest(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        Point2D nearest = new Point2D(root.p.x(), root.p.y());
        return nearest(nearest, root, p);
    }
    
    // Recursive method to find the nearest neighbor of a given point
    private Point2D nearest(Point2D nearest, Node x, Point2D p) {
        if (x == null) { return nearest; }
        if (x.p.distanceSquaredTo(p) <= p.distanceSquaredTo(nearest)) {
            nearest = x.p;
        }
        if (x.lb != null && x.rt != null) { 
            if ((x.axis == VERTICAL && p.x() < x.p.x()) || (x.axis == HORIZONTAL && p.y() < x.p.y())) {
                if (x.lb.r.distanceSquaredTo(p) <= p.distanceSquaredTo(nearest)) { nearest = nearest(nearest, x.lb, p); }
                if (x.rt.r.distanceSquaredTo(p) <= p.distanceSquaredTo(nearest)) { nearest = nearest(nearest, x.rt, p); }
            }
            else {
                if (x.rt.r.distanceSquaredTo(p) <= p.distanceSquaredTo(nearest)) { nearest = nearest(nearest, x.rt, p); }
                if (x.lb.r.distanceSquaredTo(p) <= p.distanceSquaredTo(nearest)) { nearest = nearest(nearest, x.lb, p); }
            }
        }
        else {
            if (x.lb != null) {
                if (x.lb.r.distanceSquaredTo(p) <= p.distanceSquaredTo(nearest)) { nearest = nearest(nearest, x.lb, p); }
            }
            if (x.rt != null) {
                if (x.rt.r.distanceSquaredTo(p) <= p.distanceSquaredTo(nearest)) { nearest = nearest(nearest, x.rt, p); }
            }
        }
        return nearest;
    }
    
    public static void main(String[] args) {
        KdTree kd = new KdTree();
        for (int i = 0; i < 10; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            kd.insert(new Point2D(x, y));
        }
        System.out.println(kd.size());
        for (Point2D p : kd.range(new RectHV(0, 0, 1, 1))) {
            System.out.println(p);
        }
        kd.draw();
    }
}