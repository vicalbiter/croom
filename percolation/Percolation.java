import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF gridUF;
    private WeightedQuickUnionUF gridUFV;
    private int[][] grid;
    private int count;
    private int n;
    private int topNode;
    private int bottomNode;
    
    public Percolation(int n) {
        validate(n);
        this.n = n;
        gridUF = new WeightedQuickUnionUF(n*n + 2);
        gridUFV = new WeightedQuickUnionUF(n*n + 2);
        topNode = n*n;
        bottomNode = n*n + 1;
        count = 0;
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }
    
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row-1][col-1] = 1;
            neighborUnion(row, col, row-1, col);
            neighborUnion(row, col, row+1, col);
            neighborUnion(row, col, row, col-1);
            neighborUnion(row, col, row, col+1);
            count++;
        }
    }
    
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row-1][col-1] == 1;
    }
    
    public boolean isFull(int row, int col) {
        validate(row, col);
        return gridUFV.connected((row-1)*n + (col-1), topNode);
    }
    
    public int numberOfOpenSites() {
        return count;
    }
    
    public boolean percolates() {
        return gridUF.connected(topNode, bottomNode);
    }
    
    private void neighborUnion(int row, int col, int rowNe, int colNe) {
        int p = (row-1)*n + (col-1);
        int q = (rowNe-1)*n + (colNe-1);
        if ((rowNe >= 1 && rowNe <= n) && (colNe >= 1 && colNe <= n)) { 
            if (isOpen(rowNe, colNe)) {
                gridUF.union(p, q);
                gridUFV.union(p, q);
            }
        }
        else if (rowNe == 0) {
            gridUF.union(p, topNode);
            gridUFV.union(p, topNode);
        }
        else if (rowNe == n + 1) {
            gridUF.union(p, bottomNode);
        }
    }
    
    private void validate(int... val) {
        if (val.length == 1 && val[0] <= 0) {
            throw new IllegalArgumentException("n should be > 0");
        }
        else if (val.length == 2 && (val[0] < 1 || val[0] > n || val[1] < 1 || val[1] > n)) {
            throw new IllegalArgumentException("inputs must be >= 1 and <= n");
        }
    }
    
    private void isOpenPrint(int row, int col) {
        if (isOpen(row, col)) {
            System.out.println(row + ", " + col + " is open");
        }
        else {
            System.out.println(row + ", " + col + " is closed");
        }
    }
    
    private void percolatesPrint() {
        if (percolates()) {
            System.out.println("It percolates.");
        }
    }
    
    public static void main(String[] args) {
        /*
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            percolation.open(p, q);
        }
        percolation.percolatesPrint();
        */
        Percolation percolation = new Percolation(2);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.isOpenPrint(1, 1);
        percolation.isOpenPrint(1, 2);
        percolation.isOpenPrint(2, 1);
        percolation.isOpenPrint(2, 2);
        percolation.percolatesPrint();
    }
}
