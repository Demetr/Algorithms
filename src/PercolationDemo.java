import java.lang.reflect.Method;

/****************************************************************************
 *
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation N
 *  Dependencies: StdDraw.java StdRandom.java QuickU.java
 *
 ****************************************************************************/

public class PercolationDemo {
    private int N;              // N-by-N grid
    //private QuickU UF;          // union-isSameRoot data structure
    private UF UF;
    private boolean[][] cells;  // is cell i-j occupied?, array of bool

    public PercolationDemo(int N) {
        this.N = N;
        //UF = new QuickU(N*N);
        UF = new UF(N*N);
        cells = new boolean[N][N];

        // initialize top and bottom rows
        for (int i = 0; i < N-1; i++) {
            UF.union(cell(i, 0), cell(i + 1, 0));
            UF.union(cell(i, N - 1), cell(i + 1, N - 1));
        }
        for (int i = 0; i < N; i++) {
            cells[i][N-1] = true;
            cells[i][0] = true;
        }
    }

    // display it
    public void show() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (cells[i][j]) {
                    StdDraw.filledSquare(i + 0.5, j + 0.5, 0.45);
                }
            }
        }
    }



    // choose a random site to occupy
    public void step() {
        int i, j;
        do {
            i = StdRandom.uniform(N);
            j = StdRandom.uniform(N);
        } while (cells[i][j]);
        cells[i][j] = true;
        if (i < N-1 && cells[i+1][j]) UF.union(cell(i, j), cell(i + 1, j));
        if (i > 0   && cells[i-1][j]) UF.union(cell(i, j), cell(i - 1, j));
        if (j < N-1 && cells[i][j+1]) UF.union(cell(i, j), cell(i, j + 1));
        if (j > 0   && cells[i][j-1]) UF.union(cell(i, j), cell(i, j - 1));
    }

    // occupy random sites until a spanning cluster is formed
    public int simulate() {
        for (int t = 0; true; t++) {
            if (find(cell(0, 0), cell(N - 1, N - 1), UF)) return t;
            step();
            show();
            StdDraw.show(50);
        }
    }


    // convert from i-j to unique integer
    private int cell(int i, int j) {
        return i + j*N;
    }

    public boolean find(int p, int q, UF uf) {
        return uf.find(p) == uf.find(q);
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
