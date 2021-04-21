import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private boolean[][] grid;
    private WeightedQuickUnionUF UF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) throw new java.lang.IllegalArgumentException("N can't be smaller than zero");
        this.N = N;

        grid = new boolean[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        UF = new WeightedQuickUnionUF(N*N+2);
    }    

    private void validateArguments(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N) throw new java.lang.IllegalArgumentException("Arguments out of index");
    }

    private int getIndex(int row, int col) {
        return (row - 1) * N + col;
    }

    // opens the site (row,col) if it is not open already
    public void open(int row, int col) {
        validateArguments(row, col);
        if (isOpen(row, col)) return;
        grid[row - 1][col - 1] = true;
        int idx = getIndex(row, col);
        if (row == 1) UF.union(0,idx);
        if (row == N) UF.union(N*N+1, idx);
        if (row > 1) { if (isOpen(row - 1, col)) {
            UF.union(getIndex(row - 1, col), idx);
        }};
        if (row < N) { if (isOpen(row + 1, col)) {
            UF.union(getIndex(row + 1, col), idx);
        }};
        if (col > 1) { if (isOpen(row, col - 1)) {
            UF.union(getIndex(row, col - 1), idx);
        }};
        if (col < N) { if (isOpen(row, col + 1)) {
            UF.union(getIndex(row, col + 1), idx);
        }};
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validateArguments(row,col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validateArguments(row, col);
        boolean connected = UF.find(0) == UF.find(getIndex(row, col));
        return isOpen(row, col) && connected;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j]) sum +=1;
            }
        }
        return sum;
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.find(0) == UF.find(N*N+1);
    }
}  

