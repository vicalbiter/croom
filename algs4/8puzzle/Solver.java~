import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

public class Solver {
    
    private boolean solvable = false;
    private MinPQ<Node> pqueue = new MinPQ<Node>();
    private Stack<Board> solution = new Stack<Board>();
    private int moves = -1;
    
    private class Node implements Comparable<Node> {
        
        public Board board;
        public Node pred;
        public int moves;
        public int priority;
        
        public Node(Board board, Node pred, int moves) {
            this.board = board;
            this.pred = pred;
            this.moves = moves;
            priority = moves + board.manhattan();
        }
        
        public int compareTo(Node that) {
            if (this.priority < that.priority) { return -1; }
            if (this.priority == that.priority) {
                if (this.priority - this.moves < that.priority - that.moves) { return -1; }
            }
            return 1;
        }
    }
    
    // Solve the n-puzzle using the A* algorithm (w/Manhattan heuristic)
    public Solver(Board initial) {
        
        if (initial == null) { throw new IllegalArgumentException(); }
        Node current;
        
        // Insert both the initial board and its twin into the PQ
        pqueue.insert(new Node(initial, null, 0));
        pqueue.insert(new Node(initial.twin(), null, 0));
        while (true) {
            current = pqueue.delMin();
            if (current.board.isGoal()) {
                break;
            }
            // Add each of the current board's neighbors into the PQ
            for (Board board : current.board.neighbors()) {
                if(current.moves == 0 || !board.equals(current.pred.board)) {
                    pqueue.insert(new Node(board, current, current.moves + 1));
                }
            }
        }
        
        while (current != null) {
            solution.push(current.board);
            current = current.pred;
            moves++;
        }
        
        // If the solution leads up to the initial board (not to its twin), then the board is solvable
        Board first = solution.peek();
        if (first.equals(initial)) { solvable = true; }
        else {
            moves = -1;
            solution = null;
        }
        
    }
    
    public boolean isSolvable() {
        return solvable;
    }
    
    public int moves() {
        return moves;
    }
    
    public Iterable<Board> solution() {
        return solution;
    }
    
    public static void main(String[] args) {
        
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}