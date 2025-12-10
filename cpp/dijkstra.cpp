// dijkstra_benchmark.cpp
#include <iostream>
#include <vector>
#include <queue>
#include <utility>
#include <limits>
#include <chrono>
#include <algorithm>

using namespace std;
using ll = long long;
using Clock = chrono::high_resolution_clock;
const ll INF = numeric_limits<ll>::max() / 4;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cout << "Dijkstra Benchmark (directed weighted graph)\n";
    cout << "Input format:\n";
    cout << "n m\n";
    cout << "u1 v1 w1\n";
    cout << "u2 v2 w2\n";
    cout << "... (m edges, 0-based node indices)\n";
    cout << "s   (source node, 0-based)\n\n";

    int n, m;
    if (!(cin >> n >> m) || n <= 0 || m < 0) {
        cerr << "Invalid n or m.\n";
        return 1;
    }

    vector<vector<pair<int,int>>> adj(n);
    for (int i = 0; i < m; ++i) {
        int u, v;
        long long w;
        if (!(cin >> u >> v >> w)) {
            cerr << "Invalid edge input at line " << (i+1) << ". Expect: u v w\n";
            return 1;
        }
        if (u < 0 || u >= n || v < 0 || v >= n) {
            cerr << "Edge endpoints out of range: " << u << " " << v << "\n";
            return 1;
        }
        // store weight as int if small; cast to int for storage
        adj[u].push_back({v, static_cast<int>(w)});
        // If you want an undirected graph, uncomment the next line:
        // adj[v].push_back({u, static_cast<int>(w)});
    }

    int src;
    if (!(cin >> src) || src < 0 || src >= n) {
        cerr << "Invalid source node.\n";
        return 1;
    }

    // Prepare containers
    vector<ll> dist(n, INF);
    vector<int> parent(n, -1);

    // Start timing Dijkstra (only algorithm execution)
    auto t0 = Clock::now();

    // Min-heap: (distance, node)
    using P = pair<ll,int>;
    priority_queue<P, vector<P>, greater<P>> pq;

    dist[src] = 0;
    pq.push({0, src});

    while (!pq.empty()) {
        auto [d, u] = pq.top();
        pq.pop();
        if (d != dist[u]) continue; // stale entry

        for (const auto &e : adj[u]) {
            int v = e.first;
            int w = e.second;
            ll nd = d + (ll)w;
            if (nd < dist[v]) {
                dist[v] = nd;
                parent[v] = u;
                pq.push({nd, v});
            }
        }
    }

    // End timing
    auto t1 = Clock::now();
    chrono::duration<double> elapsed = t1 - t0;

    // Output distances and paths
    cout << "\nDijkstra results from source node " << src << ":\n";
    for (int v = 0; v < n; ++v) {
        if (dist[v] == INF) {
            cout << "Node " << v << ": unreachable\n";
        } else {
            cout << "Node " << v << ": distance = " << dist[v] << ", path = ";
            // reconstruct path
            vector<int> path;
            int cur = v;
            while (cur != -1) { path.push_back(cur); cur = parent[cur]; }
            reverse(path.begin(), path.end());
            for (size_t i = 0; i < path.size(); ++i) {
                if (i) cout << " -> ";
                cout << path[i];
            }
            cout << "\n";
        }
    }

    cout << "\nDijkstra execution time: " << elapsed.count() << " seconds\n";

    return 0;
}
