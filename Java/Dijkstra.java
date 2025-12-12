// Main.java
import java.io.*;
import java.util.*;

public class Main {
    static class Edge {
        int to;
        int weight;
        Edge(int to, int weight) { this.to = to; this.weight = weight; }
    }

    static class Node implements Comparable<Node> {
        int v;
        long dist;
        Node(int v, long dist) { this.v = v; this.dist = dist; }
        public int compareTo(Node o) {
            return Long.compare(this.dist, o.dist);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // Read n and m
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        if (n <= 0 || m < 0) {
            System.err.println("Invalid n or m.");
            return;
        }

        // Build adjacency list
        ArrayList<Edge>[] adj = new ArrayList[n];
        for (int i = 0; i < n; ++i) adj[i] = new ArrayList<>();

        for (int i = 0; i < m; ++i) {
            String line = br.readLine();
            if (line == null) {
                System.err.println("Insufficient edge lines.");
                return;
            }
            st = new StringTokenizer(line);
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            if (u < 0 || u >= n || v < 0 || v >= n) {
                System.err.println("Edge endpoints out of range: " + u + " " + v);
                return;
            }
            adj[u].add(new Edge(v, w));
            // If you want undirected graph, also add:
            // adj[v].add(new Edge(u, w));
        }

        // Read source
        String sLine = br.readLine();
        if (sLine == null) {
            System.err.println("Missing source node.");
            return;
        }
        int src = Integer.parseInt(sLine.trim());
        if (src < 0 || src >= n) {
            System.err.println("Source out of range.");
            return;
        }

        final long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n];
        int[] parent = new int[n];
        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);

        // Run and time only Dijkstra
        long start = System.nanoTime();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[src] = 0;
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.v;
            long d = cur.dist;
            if (d != dist[u]) continue; // stale
            for (Edge e : adj[u]) {
                int v = e.to;
                long nd = d + (long)e.weight;
                if (nd < dist[v]) {
                    dist[v] = nd;
                    parent[v] = u;
                    pq.add(new Node(v, nd));
                }
            }
        }
        long end = System.nanoTime();

        double elapsed = (end - start) / 1e9;

        // Output distances and paths
        System.out.println("Dijkstra results from source node " + src + ":\n");
        for (int v = 0; v < n; ++v) {
            if (dist[v] == INF) {
                System.out.println("Node " + v + ": unreachable");
            } else {
                System.out.print("Node " + v + ": distance = " + dist[v] + ", path = ");
                // reconstruct path
                ArrayList<Integer> path = new ArrayList<>();
                int cur = v;
                while (cur != -1) {
                    path.add(cur);
                    cur = parent[cur];
                }
                Collections.reverse(path);
                for (int i = 0; i < path.size(); ++i) {
                    if (i > 0) System.out.print(" -> ");
                    System.out.print(path.get(i));
                }
                System.out.println();
            }
        }

        System.out.printf("%nDijkstra execution time: %.9f seconds%n", elapsed);
    }
}
