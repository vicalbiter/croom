import edu.princeton.cs.algs4.StdRandom;

public class TestBoard {

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
        System.out.println("Hamming: " + board.hamming() + ", Manhattan: " + board.manhattan());
    }
    
    public static void main(String[] args) {
        TestBoard testboard = new TestBoard();
        Board test = new Board(testboard.randomBoard(3));
        testboard.heuristics(test);
    }
    
}