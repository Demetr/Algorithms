/**
 * Created by Dimaas on 7/11/2015.
 */
public class Percolation {
    private int N;              // N-by-N grid
    private UF uf;
    //private static boolean[][] sites;  // is cell i-j occupied?, array of bool
    private BoolIndexer sites;  // is cell i-j occupied?, array of bool

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Illegal argument " + N + " is not more than 0");
        }
        this.N = N;
        uf = new UF(N * N);
//        sites = new boolean[N][N];
        sites = new BoolIndexer(N);
        // initialize top and bottom rows
        for (int i = 1; i < N; i++) {
            uf.union(cell(i, 1), cell(i + 1, 1));
            uf.union(cell(i, N - 1), cell(i + 1, N - 1));
        }
        for (int i = 1; i <= N; i++) {
            sites.set(i, N);
            sites.set(i, 1);
        }
    }

    /**
     * open site (row i, column j) if it is not open already
     *
     * @throws java.lang.IllegalArgumentException if <tt>N &lt; 0</tt>
     */
    public void open(int i, int j) {
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        sites.set(i, j);
        if (i < N - 1 && sites.get(i + 1, j)) uf.union(cell(i, j), cell(i + 1, j));
        if (i > 0 && sites.get(i - 1, j)) uf.union(cell(i, j), cell(i - 1, j));
        if (j < N - 1 && sites.get(i, j + 1)) uf.union(cell(i, j), cell(i, j + 1));
        if (j > 0 && sites.get(i, j - 1)) uf.union(cell(i, j), cell(i, j - 1));
    }

    /* is site (row i, column j) open?
    * @throws java.lang.IllegalArgumentException if <tt>N &lt; 0</tt>
    */
    public boolean isOpen(int i, int j) {
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        return sites.get(i, j);
    }

    /* is site (row i, column j) full?
    * @throws java.lang.IllegalArgumentException if <tt>N &lt; 0</tt>
    */
    public boolean isFull(int i, int j) {
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        return uf.find(cell(i, j)) == uf.find(cell(1, 1));
    }

    // does the system percolate?
    public boolean percolates() {
        //TODO: add logic that checks is top and bottom are connected
        return uf.find(cell(1, 1)) == uf.find(cell(N, N));
    }

    // convert from i-j to unique integer
    private int cell(int i, int j) {
        return i + (j - 1) * N;
    }

    private class BoolIndexer {
        private boolean[][] flags;

        public BoolIndexer(int size) {
            flags = new boolean[size][size];
        }

        public boolean get(int idx, int idy) {
            return flags[idx - 1][idy - 1];
        }

        public void set(int idx, int idy) {
            flags[idx - 1][idy - 1] = true;
        }

        public void unset(int idx, int idy) {
            flags[idx - 1][idy - 1] = false;
        }
    }

    public static void main(String[] args) {
        int N = 5;

        Percolation percolation = new Percolation(N);
        int i, j;
        i = StdRandom.uniform(1, N + 1);
        j = StdRandom.uniform(1, N + 1);
        percolation.open(i, j);
    }
}

