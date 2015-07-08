
public class Percolation {
    private int N;              // N-by-N grid
    private boolean[][] cells;  // is cell i-j occupied?, array of bool
    //TODO: because of specification I will implement array[1..N] that uses array*[0..N-1]
    //private QuickU uf;          // union-find data structure

    // create N-by-N grid, with all sites blocked
    public Percolation(int N){
        this.N = N;
        //TODO: create UnionFind object for providing array as a forest of trees
        cells = new boolean[N][N];

        // initialize top and bottom rows
        for (int i = 0; i < N-1; i++) {
            //uf.unite(cell(i, 0  ), cell(i+1, 0  ));  // init top row
            //uf.unite(cell(i, N-1), cell(i+1, N-1));  // init bottom row
        }
        for (int i = 0; i < N; i++) {
            cells[i][N-1] = true;
            cells[i][0] = true;
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j){
        //TODO: place here logic from random generation in Demo
    }

    // is site (row i, column j) open?
    // i º {1,N}, j º {1,N}
    public boolean isOpen(int i, int j) {
        //TODO: check if array[i,j] item presents in cells array with true value
        return true;
    }

    // is site (row i, column j) full?
    // i º {1,N}, j º {1,N}
    public boolean isFull(int i, int j) {
        return true;
    }

    // does the system percolate?
    public boolean percolates(){
        return false;
    }

    // test client (optional)
    public static void main(String[] args)
    {
        //test array for percolation i.e. percolates == true
    }
}

