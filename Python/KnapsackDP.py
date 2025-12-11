import time
import sys

SAFE_CELLS = 10_000_000  # heuristic threshold for (n+1)*(capacity+1)

def read_input():
    # Interactive-friendly: will also work with piped stdin
    try:
        n = int(input("Enter number of items (n): ").strip())
    except Exception:
        print("Invalid input for n.", file=sys.stderr)
        sys.exit(1)
    if n <= 0:
        print("n must be positive.", file=sys.stderr)
        sys.exit(1)

    weights = []
    values = []
    print(f"Enter {n} items as: <weight> <value> (one per line):")
    for i in range(n):
        parts = input().strip().split()
        if len(parts) < 2:
            print(f"Invalid input for item {i}.", file=sys.stderr)
            sys.exit(1)
        w = int(parts[0]); v = int(parts[1])
        if w < 0 or v < 0:
            print("Weights and values must be non-negative.", file=sys.stderr)
            sys.exit(1)
        weights.append(w); values.append(v)

    try:
        capacity = int(input("Enter knapsack capacity: ").strip())
    except Exception:
        print("Invalid capacity.", file=sys.stderr)
        sys.exit(1)
    if capacity < 0:
        print("Capacity must be non-negative.", file=sys.stderr)
        sys.exit(1)

    return n, weights, values, capacity

def knapsack_with_reconstruction(n, weights, values, capacity):
    # flat table to reduce Python-list-of-lists overhead
    cols = capacity + 1
    table = [0] * ((n + 1) * cols)
    def dp(i, w):
        return i * cols + w

    for i in range(1, n + 1):
        wi = weights[i - 1]
        vi = values[i - 1]
        base = (i - 1) * cols
        cur = i * cols
        # iterate w from 0..capacity
        for w in range(0, cols):
            without = table[base + w]
            with_item = table[base + (w - wi)] + vi if w >= wi else without
            table[cur + w] = without if without >= with_item else with_item

    max_value = table[n * cols + capacity]

    # reconstruct chosen items
    chosen = []
    w = capacity
    for i in range(n, 0, -1):
        if table[i * cols + w] != table[(i - 1) * cols + w]:
            chosen.append(i - 1)
            w -= weights[i - 1]
            if w <= 0:
                break
    chosen.reverse()
    return max_value, chosen

def knapsack_1d(n, weights, values, capacity):
    dp = [0] * (capacity + 1)
    for i in range(n):
        wi = weights[i]; vi = values[i]
        if wi > capacity:
            continue
        for c in range(capacity, wi - 1, -1):
            newv = dp[c - wi] + vi
            if newv > dp[c]:
                dp[c] = newv
    return dp[capacity]

if __name__ == "__main__":
    n, weights, values, capacity = read_input()
    required_cells = (n + 1) * (capacity + 1)

    print(f"\n(n+1)*(capacity+1) = {required_cells}", end="")
    if required_cells <= SAFE_CELLS:
        print(" <= SAFE_CELLS => using full 2D DP with reconstruction.")
    else:
        print(" > SAFE_CELLS => using memory-friendly 1D DP (no reconstruction).")

    t0 = time.perf_counter()

    if required_cells <= SAFE_CELLS:
        max_value, chosen = knapsack_with_reconstruction(n, weights, values, capacity)
    else:
        max_value = knapsack_1d(n, weights, values, capacity)
        chosen = None

    t1 = time.perf_counter()

    print(f"\nMaximum value achievable: {max_value}")
    print(f"DP execution time: {t1 - t0:.8f} seconds")

    if chosen is not None:
        if chosen:
            print("\nSelected items (0-based indices):")
            total_w = 0; total_v = 0
            for idx in chosen:
                print(f"  index {idx}: weight={weights[idx]}, value={values[idx]}")
                total_w += weights[idx]; total_v += values[idx]
            print(f"Total weight: {total_w}, Total value: {total_v}")
        else:
            print("\nNo items selected.")
    else:
        print("\n(Reconstruction skipped due to memory threshold. Reduce n or capacity to enable reconstruction.)")
