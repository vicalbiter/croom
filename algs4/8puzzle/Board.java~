import edu.princeton.cs.algs4.Stack;

public class Board {
    
    private int n;
    private int[][] board;
    
    public Board(int[][] blocks) {
        n = blocks.length;
        board = new int[n][n];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }
    
    // Board dimension
    public int dimension() {
        return n;
    }
    
    // Number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != i*n + j + 1) { count ++; }
            }
        }
        return count;    
    }
    
    // Sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    count += Math.abs(i - ((board[i][j] - 1) / n));
                    count += Math.abs(j - ((board[i][j] - 1) % n));
                }
            }
        }
        return count;
    }
    
    // Tells if this board the goal board
    public boolean isGoal() {
        if (hamming() == 0) { return true; }
        return false;
    }
    
    // Returns board that is obtained by swapping the first non-0 blocks 
    public Board twin() {
        Board twin = new Board(board);
        twin.twinSwap();
        return twin;
    }
    
    // Tells if this object (Board) is the same as another object (Board)
    public boolean equals(Object y) {
        if (y == this) { return true; }
        if (y == null) { return false; }
        Board that = (Board) y;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.board[i][j] != that.board[i][j]) { return false; }
            }
        }
        return true;
    }
    
    // Returns a stack (which implements the iterable interface) with all the neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        int zeroi = 0;
        int zeroj = 0;
        
        // Locate the empty space (zero) at the board
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) { 
                    zeroi = i;
                    zeroj = j;
                }
            }
        }
        
        // If any of the left, right, up, down tile moves are between bounds, create new boards for each of
        // them and add them into the stack
        if (validNeighbor(zeroi, zeroj-1)) {
            Board neighbor = new Board(board);
            neighbor.adjSwap(zeroi, zeroj, zeroi, zeroj-1);
            neighbors.push(neighbor);
        }
        if (validNeighbor(zeroi-1, zeroj)) {
            Board neighbor = new Board(board);
            neighbor.adjSwap(zeroi, zeroj, zeroi-1, zeroj);
            neighbors.push(neighbor);
        }
        if (validNeighbor(zeroi, zeroj+1)) {
            Board neighbor = new Board(board);
            neighbor.adjSwap(zeroi, zeroj, zeroi, zeroj+1);
            neighbors.push(neighbor);
        }
        if (validNeighbor(zeroi+1, zeroj)) {
            Board neighbor = new Board(board);
            neighbor.adjSwap(zeroi, zeroj, zeroi+1, zeroj);
            neighbors.push(neighbor);
        }
        return neighbors;
    }
    
    // Returns the string representation of this board
    public String toString() {
        String s = "" + n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s = s + " " + board[i][j];
            }
            s = s + "\n";
        }
        return s;
    }
    
    // Move a tile into the board's empty space (zero tile)
    private void adjSwap(int zeroi, int zeroj, int i, int j) {
        int temp = board[zeroi][zeroj];
        board[zeroi][zeroj] = board[i][j];
        board[i][j] = temp;
    }
    
    // Check if a move is between bounds
    private boolean validNeighbor(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) { return false; }
        return true;
    }
    
    private void twinSwap() {
        for (int i = 0; i < n*n; i++) {
            if (i > 0 && board[(i-1)/n][(i-1)%n] != 0 && board[i/n][i%n] != 0) {
                int temp = board[(i-1)/n][(i-1)%n];
                board[(i-1)/n][(i-1)%n] = board[i/n][i%n];
                board[i/n][i%n] = temp;
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        
    }
}
    
   