// Main.java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        System.out.println("Matrix Multiplication Benchmark (A x B)");
        System.out.print("Enter rows and columns of Matrix A: ");
        st = new StringTokenizer(br.readLine());
        int r1 = Integer.parseInt(st.nextToken());
        int c1 = Integer.parseInt(st.nextToken());

        System.out.print("Enter rows and columns of Matrix B: ");
        st = new StringTokenizer(br.readLine());
        int r2 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());

        if (c1 != r2) {
            System.err.println("Error: Cannot multiply. Columns of A must equal rows of B.");
            return;
        }

        long[][] A = new long[r1][c1];
        long[][] B = new long[r2][c2];
        long[][] C = new long[r1][c2];

        System.out.println("Enter elements of Matrix A (row-wise, space or newline separated):");
        // read r1*c1 numbers
        int needed = r1 * c1;
        st = new StringTokenizer("");
        for (int i = 0, filled = 0; filled < needed; ) {
            if (!st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) throw new EOFException("Unexpected EOF while reading Matrix A");
                st = new StringTokenizer(line);
                continue;
            }
            int val = Integer.parseInt(st.nextToken());
            int row = filled / c1;
            int col = filled % c1;
            A[row][col] = val;
            filled++;
        }

        System.out.println("Enter elements of Matrix B (row-wise, space or newline separated):");
        needed = r2 * c2;
        st = new StringTokenizer("");
        for (int i = 0, filled = 0; filled < needed; ) {
            if (!st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) throw new EOFException("Unexpected EOF while reading Matrix B");
                st = new StringTokenizer(line);
                continue;
            }
            int val = Integer.parseInt(st.nextToken());
            int row = filled / c2;
            int col = filled % c2;
            B[row][col] = val;
            filled++;
        }

        // Start timing multiplication
        long start = System.nanoTime();

        // Multiply using i-k-j ordering for better cache locality:
        for (int i = 0; i < r1; i++) {
            for (int k = 0; k < c1; k++) {
                long aik = A[i][k];
                for (int j = 0; j < c2; j++) {
                    C[i][j] += aik * B[k][j];
                }
            }
        }

        long end = System.nanoTime();
        double elapsedSec = (end - start) / 1e9;

        System.out.println("\nResult Matrix (A x B):");
        for (int i = 0; i < r1; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < c2; j++) {
                sb.append(C[i][j]);
                if (j + 1 < c2) sb.append(' ');
            }
            System.out.println(sb.toString());
        }

        System.out.printf("%nExecution time: %.9f seconds%n", elapsedSec);
    }
}
