import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int trials;
    private double[] results;
    private double confidence95;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException("Arguments must be greater than zero");
        this.trials = trials;
        results = new double[trials];
        confidence95 = 1.96;
        for (int t = 0; t < trials; t++){
            Percolation P = new Percolation(n);
            int i, j;
            while (!P.percolates()) {
                i = StdRandom.uniform(1, n+1);
                j = StdRandom.uniform(1, n+1);
                if (P.isOpen(i, j)) continue;
                else P.open(i, j);
            }
            results[t] = (double)P.numberOfOpenSites()/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (StdStats.mean(results) - (confidence95 * StdStats.stddev(results)))/ Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (StdStats.mean(results) + (confidence95 * StdStats.stddev(results)))/ Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats PS = new PercolationStats(N, T);
        StdOut.printf("mean                    = %f%n", PS.mean());
        StdOut.printf("stddev                  = %f%n", PS.stddev());
        StdOut.printf("95%% confidence interval = %f, %f%n", PS.confidenceLo(), PS.confidenceHi());
    }
}
