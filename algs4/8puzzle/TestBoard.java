import edu.princeton.cs.algs4.StdRandom;

public class TestBoard {

    private String endoftest = "\n********* End of Test ***********\n";
    
    // Generate a (uniformly) random nxn board   
    public int[][] randomBoard(int n) {
        int[] linearboard = new int[n*n];
        int[][] board = new int[n][n];
        
        for (int i = 0; i < n*n; i++) {
            linearboard[i] = i;
        }
        
        // Fisher-Yates shuffle
        for (int i = n*n-1; i >= 1; i--) {
            int j = StdRandom.uniform(0, i + 1);
            int temp = linearboard[j];
            linearboard[j] = linearboard[i];
            linearboard[i] = temp;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = linearboard[i*n + j];
            }
        }
        
        return board;
    }
    
    // Prints both the Hamming and Manhattan heuristics for a given board
    public void heuristics(Board board) {
        System.out.println(board.toString());
        System.out.println("Hamming: " + board.hamming() + ", Manhattan: " + board.manhattan() + "\n");
    }
    
    // Performs n Board.hamming() and Board.manhattan() tests over dim*dim boards
    public void heuristicsTest(int n, int dim) {
        System.out.println("Board.hamming() and Board.manhattan() Test\n");
        for (int i = 1; i <= n; i++) {
            System.out.println("-------- Test " + i + " --------");
            Board board = new Board(randomBoard(dim));
            heuristics(board);
        }
        System.out.println(endoftest);
    }
    
    // Performs n Board.twin() tests over dim*dim boards
    public void twinTest(int n, int dim) {
        System.out.println("Board.twin() Test\n");
        for (int i = 1; i <= n; i++) {
            System.out.println("-------- Test " + i + " --------");
            Board board = new Board(randomBoard(dim));
            System.out.println("Test board:");
            System.out.println(board.toString());
            System.out.println("Twin:");
            System.out.println(board.twin().toString());
        }
        System.out.println(endoftest);
    }
    
    // Board.equals() test
    public void equalsTest() {
        Board a = new Board(randomBoard(3));
        Board b = new Board(randomBoard(3));
        //Board c = new Board(a.board);
        
        System.out.println("Board.equals() Test\n");
        System.out.println("Test 1 (same object test): " + a.equals(a));
        //System.out.println("Test 2 (same board test): " + a.equals(c));
        System.out.println("Test 3 (different board test): " + a.equals(b));
        System.out.println("Test 4 (null test): " + a.equals(null));
        System.out.println(endoftest);
    }
    
    // Board.neighbors() test
    public void neighborsTest(int n, int dim) {
        System.out.println("Board.neighbors() Test\n");
        for (int i = 1; i <= n; i++) {
            System.out.println("-------- Test " + i + " --------");
            Board board = new Board(randomBoard(dim));
            System.out.println("Test board:");
            System.out.println(board.toString());
            int k = 1;
            for (Board neighbor : board.neighbors()) {
                System.out.println("Neighbor " + k + ":");
                System.out.println(neighbor.toString());
                k++;
            }
        }
        System.out.println(endoftest);
    }
            
    public static void main(String[] args) {
        TestBoard testboard = new TestBoard();
        //testboard.equalsTest();
        //testboard.twinTest(10, 3);
        //testboard.heuristicsTest(3, 3);
        testboard.neighborsTest(3, 3);
    }
    
}