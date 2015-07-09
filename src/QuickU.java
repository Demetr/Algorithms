public class QuickU {

    private int[] parent;
    private int components;

    // instantiate N isolated components 0 through N-1
    public QuickU(int N) {
        parent = new int[N];
        components = N;
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
    }

    // return parent of component corresponding to element x
    public int find(int x) {
        while (x != parent[x])
            x = parent[x];
        return x;
    }

    // return number of connected components
    public int components() {
        return components;
    }

    // are elements p and q in the same component?
    public boolean find(int p, int q) {
        return find(p) == find(q);
    }

    // merge components containing p and q
    public void unite(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        parent[i] = j;
        components--;
    }
}