// matrix_multiplication_benchmark.cpp
#include <iostream>
#include <vector>
#include <chrono>

using namespace std;
using Clock = chrono::high_resolution_clock;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cout << "Matrix Multiplication Benchmark (A x B)\n";

    int r1, c1, r2, c2;

    cout << "Enter rows and columns of Matrix A: ";
    cin >> r1 >> c1;

    cout << "Enter rows and columns of Matrix B: ";
    cin >> r2 >> c2;

    if (c1 != r2) {
        cerr << "Error: Matrix multiplication not possible (c1 != r2).\n";
        return 1;
    }

    vector<vector<int>> A(r1, vector<int>(c1));
    vector<vector<int>> B(r2, vector<int>(c2));

    cout << "Enter elements of Matrix A:\n";
    for (int i = 0; i < r1; i++)
        for (int j = 0; j < c1; j++)
            cin >> A[i][j];

    cout << "Enter elements of Matrix B:\n";
    for (int i = 0; i < r2; i++)
        for (int j = 0; j < c2; j++)
            cin >> B[i][j];

    vector<vector<int>> C(r1, vector<int>(c2, 0));

    // Start timer
    auto start = Clock::now();

    // Matrix multiplication O(n^3)
    for (int i = 0; i < r1; i++) {
        for (int k = 0; k < c1; k++) {      // iterate through shared dimension
            int aik = A[i][k];
            for (int j = 0; j < c2; j++) {
                C[i][j] += aik * B[k][j];
            }
        }
    }

    // End timer
    auto end = Clock::now();
    chrono::duration<double> elapsed = end - start;

    cout << "\nResult Matrix (A x B):\n";
    for (int i = 0; i < r1; i++) {
        for (int j = 0; j < c2; j++)
            cout << C[i][j] << " ";
        cout << "\n";
    }

    cout << "\nTime taken: " << elapsed.count() << " seconds\n";

    return 0;
}
