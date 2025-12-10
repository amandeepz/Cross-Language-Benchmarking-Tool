// knapsack_dp.cpp
#include <iostream>
#include <vector>
#include <chrono>
#include <algorithm>
#include <limits>

using namespace std;
using Clock = chrono::high_resolution_clock;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cout << "0/1 Knapsack DP\n";
    cout << "Enter number of items (n): ";
    int n;
    if (!(cin >> n) || n <= 0) {
        cerr << "Invalid number of items.\n";
        return 1;
    }

    vector<int> weight(n), value(n);
    cout << "Enter " << n << " items as: <weight> <value> (one per line)\n";
    for (int i = 0; i < n; ++i) {
        if (!(cin >> weight[i] >> value[i])) {
            cerr << "Invalid input for item " << i << ".\n";
            return 1;
        }
        if (weight[i] < 0 || value[i] < 0) {
            cerr << "Weights and values must be non-negative.\n";
            return 1;
        }
    }

    cout << "Enter knapsack capacity: ";
    int capacity;
    if (!(cin >> capacity) || capacity < 0) {
        cerr << "Invalid capacity.\n";
        return 1;
    }

    // Safety threshold for full 2D DP reconstruction:
    // (n+1) * (capacity+1) cells — each cell is an int (4 bytes). Cap near ~10M cells (~40MB).
    const long long SAFE_CELLS = 10'000'000LL;
    long long required_cells = (long long)(n + 1) * (long long)(capacity + 1);

    long long max_value = 0;
    vector<int> chosen_indices; // filled only if reconstruction performed

    // Start timing DP compute
    auto t0 = Clock::now();

    if (required_cells <= SAFE_CELLS) {
        // Full 2D DP table with reconstruction
        vector<int> table((n + 1) * (capacity + 1), 0);
        auto dp = [&](int i, int w) -> int& {
            return table[i * (capacity + 1) + w];
        };

        for (int i = 1; i <= n; ++i) {
            int wi = weight[i - 1];
            int vi = value[i - 1];
            for (int w = 0; w <= capacity; ++w) {
                int without = dp(i - 1, w);
                int with_item = (w >= wi) ? dp(i - 1, w - wi) + vi : without;
                dp(i, w) = max(without, with_item);
            }
        }

        max_value = dp(n, capacity);

        // Reconstruct chosen items
        int w = capacity;
        for (int i = n; i >= 1; --i) {
            if (dp(i, w) != dp(i - 1, w)) {
                chosen_indices.push_back(i - 1);
                w -= weight[i - 1];
                if (w <= 0) break;
            }
        }
        reverse(chosen_indices.begin(), chosen_indices.end());
    } else {
        // Memory friendly 1D DP (no reconstruction)
        vector<int> dp(capacity + 1, 0);
        for (int i = 0; i < n; ++i) {
            int wi = weight[i], vi = value[i];
            for (int c = capacity; c >= wi; --c) {
                dp[c] = max(dp[c], dp[c - wi] + vi);
            }
        }
        max_value = dp[capacity];
    }

    // End timing
    auto t1 = Clock::now();
    chrono::duration<double> elapsed = t1 - t0;

    // Output
    cout << "\nMaximum value achievable: " << max_value << "\n";
    cout << "DP execution time: " << elapsed.count() << " seconds\n";

    if (!chosen_indices.empty()) {
        cout << "\nSelected items (index : weight, value):\n";
        for (int idx : chosen_indices) {
            cout << idx << " : " << weight[idx] << " , " << value[idx] << "\n";
        }
    } else if (required_cells > SAFE_CELLS) {
        cout << "\n(Note: reconstruction skipped due to memory threshold. Reduce n or capacity to enable reconstruction.)\n";
    } else {
        cout << "\n(No items selected or reconstruction found none.)\n";
    }

    return 0;
}
