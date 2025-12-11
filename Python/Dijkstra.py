import time
import heapq
import sys

INF = 10**30

def dijkstra(adj, src):
    n = len(adj)
    dist = [INF] * n
    parent = [-1] * n
    dist[src] = 0
    pq = [(0, src)]  # (distance, node)

    while pq:
        d, u = heapq.heappop(pq)
        if d != dist[u]:
            continue
        for v, w in adj[u]:
            nd = d + w
            if nd < dist[v]:
                dist[v] = nd
                parent[v] = u
                heapq.heappush(pq, (nd, v))
    return dist, parent

def reconstruct_path(parent, v):
    if parent[v] == -1 and v != src_global:
        # unreachable or source with no parent
        if v == src_global and parent[v] == -1:
            return [v]  # source itself
        return None
    path = []
    cur = v
    while cur != -1:
        path.append(cur)
        cur = parent[cur]
    path.reverse()
    return path

if __name__ == "__main__":
    # Read input
    data = sys.stdin.read().strip().split()
    if not data:
        print("No input provided.", file=sys.stderr)
        sys.exit(1)

    it = iter(data)
    try:
        n = int(next(it))
        m = int(next(it))
    except StopIteration:
        print("Invalid input header (need n m).", file=sys.stderr)
        sys.exit(1)

    if n <= 0 or m < 0:
        print("Invalid n or m.", file=sys.stderr)
        sys.exit(1)

    adj = [[] for _ in range(n)]
    for _ in range(m):
        try:
            u = int(next(it)); v = int(next(it)); w = int(next(it))
        except StopIteration:
            print("Insufficient edge data.", file=sys.stderr)
            sys.exit(1)
        if not (0 <= u < n and 0 <= v < n):
            print(f"Edge endpoints out of range: {u} {v}", file=sys.stderr)
            sys.exit(1)
        adj[u].append((v, w))
        # For undirected graph, also add:
        # adj[v].append((u, w))

    try:
        src = int(next(it))
    except StopIteration:
        print("Missing source node.", file=sys.stderr)
        sys.exit(1)

    if not (0 <= src < n):
        print("Source node out of range.", file=sys.stderr)
        sys.exit(1)

    # Make src accessible to reconstruct_path
    src_global = src

    # Run Dijkstra and time only the algorithm
    start = time.perf_counter()
    dist, parent = dijkstra(adj, src)
    end = time.perf_counter()

    elapsed = end - start

    # Print results
    print(f"Dijkstra results from source node {src}:\n")
    for v in range(n):
        if dist[v] >= INF:
            print(f"Node {v}: unreachable")
        else:
            path = reconstruct_path(parent, v)
            if path is None:
                print(f"Node {v}: distance = {dist[v]}, path = (no path found)")
            else:
                path_str = " -> ".join(map(str, path))
                print(f"Node {v}: distance = {dist[v]}, path = {path_str}")
    print(f"\nDijkstra execution time: {elapsed} seconds")
