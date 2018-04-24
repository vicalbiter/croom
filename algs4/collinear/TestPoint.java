import java.util.Comparator;

public class TestPoint {
    
    public void compareTwoPoints(Point a, Point b) {
        //Print the result of the comparison
        System.out.println("Points" + a.toString() + " and " + b.toString());
        if (a.compareTo(b) == -1) { System.out.println("a < b"); }
        else if (a.compareTo(b) == 0) { System.out.println("a = b"); }
        else { System.out.println("a > b"); }
        
        //Print the slope between the two points
        System.out.println("Slope: " + a.slopeTo(b));
    }
    
    public static void main(String[] args) {
        
        //Compare two points and give their slope
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(3, 3);
        TestPoint testpoint = new TestPoint();
        testpoint.compareTwoPoints(a, b);
        testpoint.compareTwoPoints(a, c);
        
        //Test the Comparator
        Comparator<Point> comp = a.slopeOrder();
        int result = comp.compare(b, c);
        if (result == -1) { System.out.println("Slope of B < Slope of C"); }
        else if (result == 0) {System.out.println("Slope of B = Slope of C"); }
        else { System.out.println("Slope of B > Slope of C"); }
    }
}