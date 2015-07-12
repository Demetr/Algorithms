/**
 * Created by Dimaas on 7/11/2015.
 */
public class Percolation {
    private int N;              // N-by-N grid
    //private QuickU uf;          // union-isSameRoot data structure
    private UF uf;
    private boolean[][] sites;  // is cell i-j occupied?, array of bool

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Illegal argument " + N + " is not more than 0");
        }
        this.N = N;
        //uf = new QuickU(N*N);
        uf = new UF(N*N);
        sites = new boolean[N][N];

        // initialize top and bottom rows
        for (int i = 0; i < N-1; i++) {
            uf.union(cell(i, 0), cell(i + 1, 0));
            uf.union(cell(i, N-1), cell(i+1, N-1));
        }
        for (int i = 0; i < N; i++) {
            sites[i][N-1] = true;
            sites[i][0] = true;
        }
    }
    /** open site (row i, column j) if it is not open already
    * @throws java.lang.IllegalArgumentException if <tt>N &lt; 0</tt>
    */
    public void open(int i, int j)
    {
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        sites[i][j] = true;
        i--; j--;
        //sites[i][j] = true;
        if (i < N-1 && sites[i+1][j]) uf.union(cell(i, j), cell(i+1, j));
        if (i > 0   && sites[i-1][j]) uf.union(cell(i, j), cell(i-1, j));
        if (j < N-1 && sites[i][j+1]) uf.union(cell(i, j), cell(i, j+1));
        if (j > 0   && sites[i][j-1]) uf.union(cell(i, j), cell(i, j-1));
    }

    /* is site (row i, column j) open?
    * @throws java.lang.IllegalArgumentException if <tt>N &lt; 0</tt>
    */
    public boolean isOpen(int i, int j) {
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        return sites[i][j];
    }

    /* is site (row i, column j) full?
    * @throws java.lang.IllegalArgumentException if <tt>N &lt; 0</tt>
    */
    public boolean isFull(int i, int j) {
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        //TODO: add logic that checks flag in sites array
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        //TODO: add logic that checks is top and bottom are connected
        return false;
    }

    // display it
    public void show() {
        for (int i = 1; i < N+1; i++) {
            for (int j = 1; j < N+1; j++) {
                if (sites[i][j]) {
                    StdDraw.filledSquare(i + 0.5, j + 0.5, 0.45);
                }
            }
        }
    }

    // choose a random site to occupy
    public void step() {
//        int i, j;
//        do {
//            i = StdRandom.uniform(N);
//            j = StdRandom.uniform(N);
//        } while (sites[i][j]);
//        sites[i][j] = true;
//        if (i < N-1 && sites[i+1][j]) uf.union(cell(i, j), cell(i + 1, j));
//        if (i > 0   && sites[i-1][j]) uf.union(cell(i, j), cell(i - 1, j));
//        if (j < N-1 && sites[i][j+1]) uf.union(cell(i, j), cell(i, j + 1));
//        if (j > 0   && sites[i][j-1]) uf.union(cell(i, j), cell(i, j - 1));

        int i, j;
        do {
            i = StdRandom.uniform(1, N);
            j = StdRandom.uniform(1, N);
            open(i, j);
        } while (sites[i][j]);
    }

    // occupy random sites until a spanning cluster is formed
    public int simulate() {

        for (int t = 0; true; t++) {
            if (isSameRoot(cell(0, 0), cell(N - 1, N - 1))) return t;
            step();
            show();
            StdDraw.show(50);
        }
    }

    //check if p and q have the same root
    public boolean isSameRoot(int p, int q) {
        return uf.find(p) == uf.find(q);
    }

    // convert from i-j to unique integer
    private int cell(int i, int j) {
        return i + j*N;
    }

    public static void main(String[] args) {
        int N = 20;
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.show(0);  // turn off show-after-each-drawing-command mode

        PercolationDemo perc = new PercolationDemo(N);
        int t = perc.simulate();
        System.out.println("coefficient: " + (1.0 * t / (N * N)));
    }
}

