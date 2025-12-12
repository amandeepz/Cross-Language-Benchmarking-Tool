// Main.java
import java.io.*;
import java.util.*;

public class Main {
    // Safety threshold for full 2D DP (cells). Adjust if you have a lot of memory.
    private static final long SAFE_CELLS = 10_000_000L; // ~40MB for int table

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        System.out.println("0/1 Knapsack DP Benchmark");
        System.out.print("Enter number of items (n): ");
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        if (n <= 0) {
            System.err.println("n must be positive.");
            return;
        }

        int[] weight = new int[n];
        int[] value  = new int[n];

        System.out.println("Enter " + n + " items as: <weight> <value> (one per line):");
        for (int i = 0; i < n; ++i) {
            st = new StringTokenizer(br.readLine());
            weight[i] = Integer.parseInt(st.nextToken());
            value[i]  = Integer.parseInt(st.nextToken());
            if (weight[i] < 0 || value[i] < 0) {
                System.err.println("Weights and values must be non-negative.");
                return;
            }
        }

        System.out.print("Enter knapsack capacity: ");
        st = new StringTokenizer(br.readLine());
        int capacity = Integer.parseInt(st.nextToken());
        if (capacity < 0) {
            System.err.println("Capacity must be non-negative.");
            return;
        }

        long requiredCells = (long)(n + 1) * (long)(capacity + 1);
        System.out.println("\n(n+1)*(capacity+1) = " + requiredCells +
            (requiredCells <= SAFE_CELLS ? " <= SAFE_CELLS -> using 2D DP with reconstruction." 
                                        : " > SAFE_CELLS -> using memory-friendly 1D DP (no reconstruction)."));

        int maxValue = 0;
        List<Integer> chosen = null; // indices chosen, if reconstructed

        // Start timing DP computation
        long t0 = System.nanoTime();

        if (requiredCells <= SAFE_CELLS) {
            // Full 2D DP using flattened int array for speed
            int cols = capacity + 1;
            int[] table = new int[(n + 1) * cols];
            // dp(i,w) -> table[i*cols + w]
            for (int i = 1; i <= n; ++i) {
                int wi = weight[i - 1];
                int vi = value[i - 1];
                int prevBase = (i - 1) * cols;
                int curBase  = i * cols;
                for (int w = 0; w <= capacity; ++w) {
                    int without = table[prevBase + w];
                    int withItem = (w >= wi) ? table[prevBase + (w - wi)] + vi : without;
                    table[curBase + w] = Math.max(without, withItem);
                }
            }
            maxValue = table[n * (capacity + 1) + capacity];

            // Reconstruct chosen items
            chosen = new ArrayList<>();
            int w = capacity;
            for (int i = n; i >= 1; --i) {
                int cur = i * (capacity + 1) + w;
                int prev = (i - 1) * (capacity + 1) + w;
                if (table[cur] != table[prev]) {
                    // item i-1 was taken
                    chosen.add(i - 1);
                    w -= weight[i - 1];
                    if (w <= 0) break;
                }
            }
            Collections.reverse(chosen);
        } else {
            // Memory-efficient 1D DP (no reconstruction)
            int[] dp = new int[capacity + 1];
            for (int i = 0; i < n; ++i) {
                int wi = weight[i];
                int vi = value[i];
                if (wi > capacity) continue;
                for (int c = capacity; c >= wi; --c) {
                    int cand = dp[c - wi] + vi;
                    if (cand > dp[c]) dp[c] = cand;
                }
            }
            maxValue = dp[capacity];
        }

        // End timing
        long t1 = System.nanoTime();
        double elapsedSeconds = (t1 - t0) / 1e9;

        // Output
        System.out.println("\nMaximum value achievable: " + maxValue);
        System.out.printf("DP execution time: %.9f seconds%n", elapsedSeconds);

        if (chosen != null) {
            if (chosen.isEmpty()) {
                System.out.println("\nNo items selected.");
            } else {
                System.out.println("\nSelected items (0-based index : weight, value):");
                int totalW = 0, totalV = 0;
                for (int idx : chosen) {
                    System.out.println(idx + " : " + weight[idx] + " , " + value[idx]);
                    totalW += weight[idx];
                    totalV += value[idx];
                }
                System.out.println("Total weight: " + totalW + ", Total value: " + totalV);
            }
        } else {
            System.out.println("\n(Decision reconstruction skipped due to memory threshold. Reduce n or capacity to enable reconstruction.)");
        }
    }
}
