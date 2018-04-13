import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while(!StdIn.isEmpty()) {
            randq.enqueue(StdIn.readString());
        }
        
        Iterator<String> it = randq.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(it.next());
        }
    }
}