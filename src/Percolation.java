
public class Percolation {
    private int N;              // N-by-N grid

    //TODO: because of specification I will implement array[1..N] that uses array*[0..N-1]
    private BoolIndexer cells;  // is cell i-j occupied?, array of bool

    private WeightedQuickUnionUF uf;


    //private QuickU uf;          // union-find data structure

    // create N-by-N grid, with all sites blocked
    public Percolation(int N){
        if (N <= 0) {
            throw new IllegalArgumentException("Illegal argument " + N + " is not more than 0");
        }
        this.N = N;
        uf = new WeightedQuickUnionUF(N);

        //added support for addressing with indexes {1,N}
        for (int i = 1; i <= N; i++) {
            // initialize top and bottom rows
            uf.union(cell(i, 1), cell(i + 1, 1));
            uf.union(cell(i, N), cell(i + 1, N));

            // set top and bottom rows' cells to true
            cells.set(true, i, 1);
            cells.set(true, i, N);
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j){
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        //TODO: place here logic from random generation in Demo
    }

    // is site (row i, column j) open?
    // i ϵ {1,N}, j ϵ {1,N}
    public boolean isOpen(int i, int j) {
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        //TODO: check if array[i,j] item presents in cells array with true value
        return false;
    }

    // is site (row i, column j) full?
    // i ϵ {1,N}, j ϵ {1,N}
    public boolean isFull(int i, int j) {
        if (i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("Indexes of array is not more than 0");
        }
        return true;
    }

    // does the system percolate?
    public boolean percolates(){
        return false;
    }

    // convert from i-j to unique integer
    private int cell(int i, int j) {
        //added support for addressing with indexes {1,N}
        return i-1 + (j-1)*N;
    }

    // display it
    public void show() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (cells.get(i,j)) {
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
        } while (cells.get(i,j));
        cells.set(true, i, j);
        if (i < N && cells.get(i+1,j)) uf.union(cell(i, j), cell(i + 1, j));
        if (i > 1 && cells.get(i-1,j)) uf.union(cell(i, j), cell(i - 1, j));
        if (j < N && cells.get(i,j+1)) uf.union(cell(i, j), cell(i, j + 1));
        if (j > 1 && cells.get(i,j-1)) uf.union(cell(i, j), cell(i, j-1));
    }

    // occupy random sites until a spanning cluster is formed
    public int simulate() {
        for (int t = 0; true; t++) {
            if (uf.find(cell(0, 0), cell(N-1, N-1))) return t;
            step();
            show();
            StdDraw.show(50);
        }
    }

    private class BoolIndexer {
        private boolean[][] flags;

        public BoolIndexer(int size) {
            flags = new boolean[size][size];

            /*for (int i = 0; i < size; i++) {
                flags[i] = "empty";
            }*/
        }

        //added support for addressing with indexes {1,N}
        public boolean get(int posx, int posy) {
            return flags[posx-1][posy-1];
        }

        //added support for addressing with indexes {1,N}
        public void set(boolean s, int idx, int idy) {
            flags[idx-1][idy-1] = s;
        }
    }

    private class WeightedQuickUnionUF {
        private int[] parent;   // parent[i] = parent of i
        private int[] size;     // size[i] = number of objects in subtree rooted at i
        private int count;      // number of components

        /**
         * Initializes an empty union-find data structure with N isolated components 0 through N-1.
         *
         * @param N the number of objects
         * @throws java.lang.IllegalArgumentException if N < 0
         */
        public WeightedQuickUnionUF(int N) {
            count = N;
            parent = new int[N];
            size = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        /**
         * Returns the number of components.
         *
         * @return the number of components (between 1 and N)
         */
        public int count() {
            return count;
        }

        /**
         * Returns the component identifier for the component containing site <tt>p</tt>.
         *
         * @param p the integer representing one site
         * @return the component identifier for the component containing site <tt>p</tt>
         * @throws java.lang.IndexOutOfBoundsException unless 0 <= p < N
         */
        public int find(int p) {
            validate(p);
            while (p != parent[p])
                p = parent[p];
            return p;
        }

        // are elements p and q in the same component?
        public boolean find(int p, int q) {
            return find(p) == find(q);
        }

        // validate that p is a valid index
        private void validate(int p) {
            int N = parent.length;
            //TODO: think about validation for result of cell method
            /*if (cell(p < 0 || p > N) {
                throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + N);
            }*/
        }

        /**
         * Are the two sites <tt>p</tt> and <tt>q</tt> in the same component?
         *
         * @param p the integer representing one site
         * @param q the integer representing the other site
         * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt>
         * are in the same component, and <tt>false</tt> otherwise
         * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
         */
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }


        /**
         * Merges the component containing site<tt>p</tt> with the component
         * containing site <tt>q</tt>.
         *
         * @param p the integer representing one site
         * @param q the integer representing the other site
         * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
         */
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // make smaller root point to larger one
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            } else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
            count--;
        }
    }

    // test client (optional)
    public static void main(String[] args)
    {
        //test array for percolation i.e. percolates == true
        int N = 20;
        StdDraw.setXscale(1, N+1);
        StdDraw.setYscale(1, N+1);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.show(0);  // turn off show-after-each-drawing-command mode

        Percolation perc = new Percolation(N);
        int t = perc.simulate();
        System.out.println("coefficient: " + (1.0 * t / (N * N)));
    }
}

