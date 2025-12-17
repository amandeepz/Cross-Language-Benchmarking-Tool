using System;
using System.Diagnostics;

class Program
{
    static void Main()
    {
        Console.Write("Enter number of items: ");
        int n = int.Parse(Console.ReadLine());

        int[] weight = new int[n];
        int[] value = new int[n];

        Console.WriteLine("Enter items as: <weight> <value>");
        for (int i = 0; i < n; i++)
        {
            string[] parts = Console.ReadLine().Split();
            weight[i] = int.Parse(parts[0]);
            value[i] = int.Parse(parts[1]);
        }

        Console.Write("Enter knapsack capacity: ");
        int capacity = int.Parse(Console.ReadLine());

        int[,] dp = new int[n + 1, capacity + 1];

        Stopwatch sw = Stopwatch.StartNew();

        // 0/1 Knapsack DP
        for (int i = 1; i <= n; i++)
        {
            for (int w = 0; w <= capacity; w++)
            {
                if (weight[i - 1] <= w)
                {
                    int take = value[i - 1] + dp[i - 1, w - weight[i - 1]];
                    int skip = dp[i - 1, w];
                    dp[i, w] = Math.Max(take, skip);
                }
                else
                {
                    dp[i, w] = dp[i - 1, w];
                }
            }
        }

        sw.Stop();

        Console.WriteLine("\nMaximum value achievable: " + dp[n, capacity]);
        Console.WriteLine("Execution time: " + sw.Elapsed.TotalSeconds + " seconds");

        // Optional: reconstruct selected items
        Console.WriteLine("\nSelected items (0-based index):");
        int remainingCapacity = capacity;
        for (int i = n; i > 0; i--)
        {
            if (dp[i, remainingCapacity] != dp[i - 1, remainingCapacity])
            {
                Console.WriteLine(
                    "Item " + (i - 1) +
                    " (weight=" + weight[i - 1] +
                    ", value=" + value[i - 1] + ")"
                );
                remainingCapacity -= weight[i - 1];
            }
        }
    }
}
