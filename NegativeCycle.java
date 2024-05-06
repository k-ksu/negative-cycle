import java.util.*;

public class NegativeCycle<V> {

    static final int INF = 100000;

    static class Graph<V> {
        Map<V, Map<V, Integer>> adjacencyList;

        public Graph() {
            this.adjacencyList = new HashMap<>();
        }

        public void addVertex(V vertex) {
            if (!adjacencyList.containsKey(vertex)) {
                adjacencyList.put(vertex, new HashMap<>());
            }
        }

        public void addEdge(V source, V destination, int weight) {
            addVertex(source);
            addVertex(destination);
            adjacencyList.get(source).put(destination, weight);
        }

        public void KseniaKorchagina_sp() {
            int N = adjacencyList.size();
            int[][] dist = new int[N][N];
            int[][] next = new int[N][N];

            // Initialize distances and next array
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (adjacencyList.containsKey(i) && adjacencyList.get(i).containsKey(j)) {
                        dist[i][j] = adjacencyList.get(i).get(j);
                        next[i][j] = j;
                    } else {
                        dist[i][j] = INF;
                        next[i][j] = -1;
                    }
                }
            }

            // Floyd-Warshall algorithm
            for (int k = 0; k < N; k++) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }

            // Check for negative cycles
            for (int i = 0; i < N; i++) {
                if (dist[i][i] < 0) {
                    boolean hasNegativeCycle = true;
                    List<Integer> cycle = new ArrayList<>();
                    int cur = i;
                    do {
                        cycle.add(cur); // Vertices are 0-indexed
                        cur = next[cur][i];
                    } while (cur != i);
                    cycle.add(cur); // Vertices are 0-indexed

                    System.out.println("YES");
                    System.out.println(cycle.size() - 1);
                    for (int j = 1; j < cycle.size(); j++) {
                        System.out.print((cycle.get(j) + 1) + " "); // Convert to 1-indexed for output
                    }
                    System.out.println();
                    return;
                }
            }
            System.out.println("NO");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        Graph<Integer> graph = new Graph<>();

        // Reading the adjacency matrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int weight = scanner.nextInt();
                if (weight != INF) {
                    graph.addEdge(i, j, weight);
                }
            }
        }

        // Use Floyd-Warshall algorithm to find negative cycles
        graph.KseniaKorchagina_sp();
    }
}
