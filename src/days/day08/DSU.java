package days.day08;

/**
 * Disjoint Set Union (DSU) or Union-Find data structure.
 * <p>
 * Provides efficient operations for maintaining a collection of disjoint sets,
 * supporting union and find operations with path compression and union by size.
 * </p>
 */
public class DSU {

    /** Parent of each element; parent[i] = i if i is a root. */
    private final int[] parent;

    /** Size of the set rooted at each element. */
    private final int[] size;

    /**
     * Initializes a DSU for {@code n} elements, each in its own set.
     *
     * @param n the number of elements
     */
    public DSU(int n) {
        this.parent = new int[n];
        this.size = new int[n];

        for (int i = 0; i < n; i++) {
            this.parent[i] = i;
            this.size[i] = 1;
        }
    }

    /**
     * Finds the representative (root) of the set containing element {@code a}.
     * <p>
     * Applies path compression for efficiency.
     * </p>
     *
     * @param a the element whose set representative is sought
     * @return the root of the set containing {@code a}
     */
    public int find(int a) {
        if (parent[a] != a)
            parent[a] = find(parent[a]);
        return parent[a];
    }

    /**
     * Unites the sets containing elements {@code a} and {@code b}.
     * <p>
     * Uses union by size to keep the tree shallow, improving performance
     * of subsequent find operations.
     * </p>
     *
     * @param a an element in the first set
     * @param b an element in the second set
     */
    public void union(int a, int b) {
        int ra = find(a);
        int rb = find(b);
        if (ra == rb) return;

        if (size[ra] < size[rb]) {
            int tmp = ra; ra = rb; rb = tmp;
        }
        parent[rb] = ra;
        size[ra] += size[rb];
    }
}