import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] a = (Item[]) new Object[1];
    private int n = 0;
    
    public RandomizedQueue() {
        
    }
    
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        int k = 0;
        for (int i = 0; i < n; i++) {
            temp[k] = a[i];
            k++;
        }
        a = temp;
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    public void enqueue(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        if (n == a.length) { resize(2*a.length); }
        a[n] = item;
        n++;
    }
    
    public Item dequeue() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        int k = StdRandom.uniform(0, n);
        Item item = a[k];
        a[k] = a[n-1];
        a[n-1] = null;
        n--;
        if (n > 0 && n == a.length/4) { resize(a.length/2); }
        return item;
    }
    
    public Item sample() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        int k = StdRandom.uniform(1, n + 1);
        Item item = a[k-1];
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new RandQIterator();
    }
    
    private class RandQIterator implements Iterator<Item> {
        
        private Item[] rand = (Item[]) new Object[a.length];
        private int k = 0;
        
        public RandQIterator() {
            
            //Copy a[] into a new array rand[]
            for (int i = 0; i < n; i++) {
                rand[i] = a[i];
            }
            
            //Apply Fisher-Yates shuffle algorithm over rand[]
            for (int i = n-1; i >= 1; i--) {
                int j = StdRandom.uniform(0, i + 1);
                Item temp = rand[j];
                rand[j] = rand[i];
                rand[i] = temp;
            }
        }
        
        public boolean hasNext() {
            return k < n;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (k >= n) { throw new NoSuchElementException(); }
            return rand[k++];
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> randq = new RandomizedQueue<String>();
        randq.enqueue("ola");
        randq.enqueue("ke");
        randq.enqueue("ase");
        randq.enqueue("trabajando");
        randq.enqueue("o");
        randq.enqueue("ke");
        randq.enqueue("ase");
        
        for (String s : randq) {
            System.out.println(s);
        }
        
        Iterator<String> it = randq.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
    