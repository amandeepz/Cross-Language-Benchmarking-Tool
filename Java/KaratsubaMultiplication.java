// Main.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Locale;

public class Main {

    // Trim leading zeros, keep "0" for zero
    private static String trimLeadingZeros(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') i++;
        return s.substring(i);
    }

    // Add two non-negative numeric strings
    private static String addStrings(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry != 0) {
            int da = (i >= 0) ? (a.charAt(i) - '0') : 0;
            int db = (j >= 0) ? (b.charAt(j) - '0') : 0;
            int sum = da + db + carry;
            sb.append((char)('0' + (sum % 10)));
            carry = sum / 10;
            i--; j--;
        }
        return trimLeadingZeros(sb.reverse().toString());
    }

    // Subtract b from a (assumes a >= b, both non-negative numeric strings)
    private static String subtractStrings(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;
        int borrow = 0;
        while (i >= 0) {
            int da = a.charAt(i) - '0';
            int db = (j >= 0) ? (b.charAt(j) - '0') : 0;
            int diff = da - db - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            sb.append((char)('0' + diff));
            i--; j--;
        }
        return trimLeadingZeros(sb.reverse().toString());
    }

    // Multiply numeric string by 10^n (append n zeros)
    private static String shiftLeft(String s, int n) {
        if (s.equals("0")) return "0";
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < n; ++i) sb.append('0');
        return sb.toString();
    }

    // Karatsuba multiplication on numeric strings
    private static String karatsuba(String x, String y) {
        x = trimLeadingZeros(x);
        y = trimLeadingZeros(y);

        // Base case: when numbers are small, fall back to BigInteger multiplication
        if (x.length() <= 9 && y.length() <= 9) { // 9 digits safe to fit in 32-bit multiply range
            long a = Long.parseLong(x);
            long b = Long.parseLong(y);
            return Long.toString(a * b);
        }

        int n = Math.max(x.length(), y.length());
        if (n % 2 != 0) n++; // make even for balanced split

        // Left-pad with zeros so both have length n
        x = String.format(Locale.ROOT, "%" + n + "s", x).replace(' ', '0');
        y = String.format(Locale.ROOT, "%" + n + "s", y).replace(' ', '0');

        int half = n / 2;
        String aL = x.substring(0, n - half);
        String aR = x.substring(n - half);
        String bL = y.substring(0, n - half);
        String bR = y.substring(n - half);

        String p1 = karatsuba(aL, bL);
        String p2 = karatsuba(aR, bR);
        String sumA = addStrings(aL, aR);
        String sumB = addStrings(bL, bR);
        String p3 = karatsuba(sumA, sumB);

        // mid = p3 - p1 - p2
        String temp = subtractStrings(p3, p1);
        String mid = subtractStrings(temp, p2);

        String result = addStrings(
                addStrings(shiftLeft(p1, 2 * half), shiftLeft(mid, half)),
                p2
        );
        return trimLeadingZeros(result);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Karatsuba Multiplication (string-based)");

        System.out.print("Enter first large integer: ");
        String x = br.readLine().trim();
        System.out.print("Enter second large integer: ");
        String y = br.readLine().trim();

        // validate (non-negative integers)
        if (!x.matches("\\d+") || !y.matches("\\d+")) {
            System.err.println("Invalid input: please enter non-negative integer strings (digits only).");
            return;
        }

        long start = System.nanoTime();
        String product = karatsuba(x, y);
        long end = System.nanoTime();

        double elapsedSec = (end - start) / 1e9;

        System.out.println("\nProduct:");
        System.out.println(product);
        System.out.printf("%nExecution time: %.9f seconds%n", elapsedSec);
    }
}
