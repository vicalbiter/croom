import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private int trials;
    private double[] percOpen;
    
    public PercolationStats(int n, int trials) {
        validate(n);
        validate(trials);
        this.trials = trials;
        percOpen = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randrow = StdRandom.uniform(1, n + 1);
                int randcol = StdRandom.uniform(1, n + 1);
                percolation.open(randrow, randcol);
            }
            percOpen[i] = percolation.numberOfOpenSites() / ((double) n*n);
        }
    }
    
    public double mean() {
        return StdStats.mean(percOpen);
    }
    
    public double stddev() {
        return StdStats.stddev(percOpen);
    }
    
    public double confidenceLo() {
        return mean() - (1.96*stddev()/Math.sqrt(trials));
    }
    
    public double confidenceHi() {
        return mean() + (1.96*stddev()/Math.sqrt(trials));
    }
    
    private void validate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("input should be > 0");
        }
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);
        System.out.println("mean \t\t\t= " + percStats.mean());
        System.out.println("stddev \t\t\t= " + percStats.stddev());
        System.out.println("95% confidence interval = [" +
                           percStats.confidenceLo() + ", " +
                           percStats.confidenceHi() + "]");
    }
}