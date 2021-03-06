import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private Node front;
    private Node end;
    private int n = 0;
    
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    
    public Deque() {

    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    public void addFirst(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        else {
            Node oldfront = front;
            front = new Node();
            front.item = item;
            front.next = null;
            front.prev = oldfront;
            if (n > 0) { oldfront.next = front; }
            n++;
            if (n == 1) { end = front; }
        }
    }
    
    public void addLast(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        else {
            Node oldend = end;
            end = new Node();
            end.item = item;
            end.next = oldend;
            end.prev = null;
            if (n > 0) { oldend.prev = end; }
            n++;
            if (n == 1) { front = end; }
        }
    }
    
    public Item removeFirst() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        else if (n > 1) {
            Item item = front.item;
            front = front.prev;
            front.next = null;
            n--;
            if (n == 1) { end = front; }
            return item;
        }
        else {
            Item item = front.item;
            front = null;
            end = null;
            n--;
            return item;
        }
    }
    
    public Item removeLast() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        else if (n > 1) {
            Item item = end.item;
            end = end.next;
            end.prev = null;
            n--;
            if (n == 1) { front = end; }
            return item;
        }
        else {
            Item item = end.item;
            front = null;
            end = null;
            n--;
            return item;
        }
    }
    
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        
        private Node current = front;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (current == null) { throw new NoSuchElementException(); }
            Item item = current.item;
            current = current.prev;
            return item;
        }
    }
    
    private void printSize() {
        if(isEmpty()) {
            System.out.println("The deque is empty");
        }
        else {
            System.out.println("The deque has " + n + " elements");
        }
    }
    
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        
        //Add 10 items at the front, and remove them from the end
        for (int i = 1; i <= 10; i++) {
            String item = " Element : " + i + ".";
            deque.addFirst(item);
            deque.printSize();
        }
        for (int i = 1; i <= 10; i++) {
            String item = deque.removeLast();
            System.out.println(item);
        }
        deque.printSize();
        
        //Add odd items at the end, and even items at the front, and then remove from the front
        for (int i = 1; i <= 10; i++) {
            String item = "" + i;
            if (i % 2 == 0) {
                deque.addFirst(item);
            }
            else {
                deque.addLast(item);
            }
            deque.printSize();
        }
        for (int i = 1; i <= 10; i++) {
            String item = deque.removeFirst();
            System.out.println(item);
        }
        deque.printSize();
        
        //Test the iterator 
        int k = 1;
        for (int i = 1; i <= 10; i++) {
            String item = "" + i;
            deque.addLast(item);
        }
        deque.printSize();
        for (String s : deque) {
            System.out.println("I found the element " + s + ".");
            k++;
        }
    }
}

   
        