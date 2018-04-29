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
    
    public int dimension() {
        return n;
    }
    
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != i*n + j + 1) { count ++; }
            }
        }
        return count;    
    }
    
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
    
    public boolean isGoal() {
        if (hamming() == 0) { return true; }
        return false;
    }
    
    public Board twin() {
        return null;
    }
    
    public boolean equals(Object y) {
        return false;
    }
    
    public Iterable<Board> neighbors() {
        return null;
    }
    
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
    
    public static void main(String[] args) {
        
        int[][] testboard = new int[3][3];
        for (int i = 0; i < testboard.length; i++) {
            for (int j = 0; j < testboard.length; j++) {
                testboard[i][j] = 3*i + j + 1;
            }
        }
        testboard[2][2] = 1;
        testboard[0][0] = 0;
        
        Board board = new Board(testboard);
        System.out.println(board.toString());
        System.out.println(board.manhattan());
    }
}
    
   