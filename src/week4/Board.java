import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int N;
    private final int[][] tiles;
    private int xZero = -1;
    private int yZero = -1;

    // 2d array clone helper function
    private int[][] copyOf(int[][] array2d) {
        int[][] clone = new int[array2d.length][];
        for (int row = 0; row < array2d.length; row++) {
            clone[row] = array2d[row].clone();
        }
        return clone;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.N = tiles[0].length;
        this.tiles = copyOf(tiles);
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (this.tiles[i][j]==0) {
                    xZero = i;
                    yZero = j;
                    break;
                }
            }
        }
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(this.N).append("\n");
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < N; j++) {
                res.append(this.tiles[i][j]).append(" ");
            }
            res.append("\n");
        }      
        return res.toString();  
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] == 0) continue;
                if (this.tiles[i][j] != (i*this.N) + (j+1)) count++; 
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int val;
        int dif_x = 0, dif_y = 0;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                val = this.tiles[i][j]-1;
                if (val==-1) continue;
                dif_y += Math.abs(val / this.N - i);
                dif_x += Math.abs(val % this.N - j);
            }
        }
        return dif_x + dif_y;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (manhattan()==0) return true;
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)  return true;
        if (y == null) return false;
        if (y.getClass() != getClass()) return false;
        Board that = (Board)y;
        if (that.dimension() != this.N) return false;

        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++){
                if (this.tiles[i][j]!=that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    private Board swapTiles(int x1, int y1, int x2, int y2) {
        int[][] temp = copyOf(this.tiles);
        int val;
        val = temp[x1][y1];
        temp[x1][y1] = temp[x2][y2];
        temp[x2][y2] = val;
        return new Board(temp);        
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Board temp;
        ArrayList<Board> boardArrayList = new ArrayList<Board>();

        if (xZero > 0) {
            temp = swapTiles(xZero, yZero, xZero-1, yZero);
            boardArrayList.add(temp);
        }

        if (xZero < this.N - 1) {
            temp = swapTiles(xZero, yZero, xZero+1, yZero);
            boardArrayList.add(temp);
        }

        if (yZero > 0) {
            temp = swapTiles(xZero, yZero, xZero, yZero-1);
            boardArrayList.add(temp);
        }

        if (yZero < N - 1) {
            temp = swapTiles(xZero, yZero, xZero, yZero+1);
            boardArrayList.add(temp);
        }

        return boardArrayList;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int i = 0, j = 0;
        outerloop:
        for (; i < this.N; i++) {
            j = 0;
            for (; j < this.N - 1; j++) {
                int first = tiles[i][j];
                int second = tiles[i][j+1];
                if (first != 0 && second != 0) {
                    break outerloop;
                }
            }
        }
        Board twin = swapTiles(i, j, i, j + 1);
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board b = new Board(m);
        StdOut.println(b);
        StdOut.println("_______________");
        for (Board a: b.neighbors()) {
            StdOut.println(a);
        }
    }
}