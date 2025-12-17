using System;
using System.Diagnostics;

class Program
{
    static void Main()
    {
        Console.Write("Enter rows and columns of Matrix A: ");
        string[] partsA = Console.ReadLine().Split();
        int r1 = int.Parse(partsA[0]);
        int c1 = int.Parse(partsA[1]);

        Console.Write("Enter rows and columns of Matrix B: ");
        string[] partsB = Console.ReadLine().Split();
        int r2 = int.Parse(partsB[0]);
        int c2 = int.Parse(partsB[1]);

        if (c1 != r2)
        {
            Console.WriteLine("Matrix multiplication not possible (columns of A must equal rows of B).");
            return;
        }

        int[,] A = new int[r1, c1];
        int[,] B = new int[r2, c2];
        int[,] C = new int[r1, c2];

        Console.WriteLine("Enter elements of Matrix A row-wise:");
        for (int i = 0; i < r1; i++)
        {
            string[] row = Console.ReadLine().Split();
            for (int j = 0; j < c1; j++)
                A[i, j] = int.Parse(row[j]);
        }

        Console.WriteLine("Enter elements of Matrix B row-wise:");
        for (int i = 0; i < r2; i++)
        {
            string[] row = Console.ReadLine().Split();
            for (int j = 0; j < c2; j++)
                B[i, j] = int.Parse(row[j]);
        }

        Stopwatch sw = Stopwatch.StartNew();

        // Matrix multiplication
        for (int i = 0; i < r1; i++)
        {
            for (int k = 0; k < c1; k++)
            {
                int a = A[i, k];
                for (int j = 0; j < c2; j++)
                {
                    C[i, j] += a * B[k, j];
                }
            }
        }

        sw.Stop();

        Console.WriteLine("\nResultant Matrix (A x B):");
        for (int i = 0; i < r1; i++)
        {
            for (int j = 0; j < c2; j++)
                Console.Write(C[i, j] + " ");
            Console.WriteLine();
        }

        Console.WriteLine("\nExecution time: " + sw.Elapsed.TotalSeconds + " seconds");
    }
}
