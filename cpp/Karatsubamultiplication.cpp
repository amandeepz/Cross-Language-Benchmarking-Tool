// karatsuba_multiplication.cpp
#include <iostream>
#include <string>
#include <algorithm>
#include <chrono>
#include <vector>

using namespace std;
using Clock = chrono::high_resolution_clock;

// Remove leading zeros from a numeric string
string trimZeros(const string &s) {
    string r = s;
    while (r.size() > 1 && r[0] == '0') r.erase(r.begin());
    return r;
}

// Add two numbers represented as strings
string addStrings(const string &a, const string &b) {
    string res = "";
    int carry = 0, i = a.size() - 1, j = b.size() - 1;

    while (i >= 0 || j >= 0 || carry) {
        int sum = carry;
        if (i >= 0) sum += a[i--] - '0';
        if (j >= 0) sum += b[j--] - '0';
        carry = sum / 10;
        res.push_back(char((sum % 10) + '0'));
    }
    reverse(res.begin(), res.end());
    return trimZeros(res);
}

// Subtract b from a (a >= b guaranteed)
string subtractStrings(const string &a, const string &b) {
    string res = "";
    int borrow = 0, i = a.size() - 1, j = b.size() - 1;

    while (i >= 0) {
        int diff = (a[i] - '0') - borrow - (j >= 0 ? (b[j] - '0') : 0);
        if (diff < 0) diff += 10, borrow = 1;
        else borrow = 0;
        res.push_back(char(diff + '0'));
        i--; j--;
    }

    reverse(res.begin(), res.end());
    return trimZeros(res);
}

// Multiply number by 10^n (append zeros)
string shiftLeft(const string &s, int n) {
    return s + string(n, '0');
}

// Karatsuba recursive multiplication
string karatsuba(const string &x, const string &y) {
    string a = trimZeros(x);
    string b = trimZeros(y);

    // Base case for small numbers (direct multiplication)
    if (a.size() < 10 && b.size() < 10) {
        long long n1 = stoll(a);
        long long n2 = stoll(b);
        return to_string(n1 * n2);
    }

    int n = max(a.size(), b.size());
    if (n % 2) n++; // make even length

    // Pad with leading zeros
    while ((int)a.size() < n) a = "0" + a;
    while ((int)b.size() < n) b = "0" + b;

    int half = n / 2;

    // Split into halves
    string aL = a.substr(0, n - half);
    string aR = a.substr(n - half);
    string bL = b.substr(0, n - half);
    string bR = b.substr(n - half);

    // Karatsuba recursive calls
    string p1 = karatsuba(aL, bL);
    string p2 = karatsuba(aR, bR);
    string p3 = karatsuba(addStrings(aL, aR), addStrings(bL, bR));

    // Combine results:
    // result = p1 * 10^(2*half) + (p3 - p1 - p2) * 10^(half) + p2
    string mid = subtractStrings(subtractStrings(p3, p1), p2);

    string result = addStrings(
        addStrings(shiftLeft(p1, 2 * half), shiftLeft(mid, half)),
        p2
    );

    return trimZeros(result);
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cout << "Karatsuba Multiplication Benchmark\n";
    cout << "Enter first large integer: ";
    string x;
    cin >> x;

    cout << "Enter second large integer: ";
    string y;
    cin >> y;

    // Start timing
    auto start = Clock::now();

    string result = karatsuba(x, y);

    // End timing
    auto end = Clock::now();
    chrono::duration<double> elapsed = end - start;

    cout << "\nProduct:\n" << result << "\n";
    cout << "\nExecution time: " << elapsed.count() << " seconds\n";

    return 0;
}
