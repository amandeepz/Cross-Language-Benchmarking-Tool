using System;
using System.Diagnostics;

class Program
{
    // Partition function (Lomuto scheme)
    static int Partition(int[] arr, int low, int high)
    {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++)
        {
            if (arr[j] <= pivot)
            {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp2 = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp2;

        return i + 1;
    }

    // Quick Sort recursive function
    static void QuickSort(int[] arr, int low, int high)
    {
        if (low < high)
        {
            int pi = Partition(arr, low, high);
            QuickSort(arr, low, pi - 1);
            QuickSort(arr, pi + 1, high);
        }
    }

    static void Main()
    {
        Console.Write("Enter number of elements: ");
        int n = int.Parse(Console.ReadLine());

        int[] arr = new int[n];

        if (n > 0)
        {
            Console.WriteLine("Enter elements separated by space:");
            string inputLine = Console.ReadLine();
            string[] parts = inputLine.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);

            for (int i = 0; i < n; i++)
                arr[i] = int.Parse(parts[i]);
        }

        Stopwatch sw = Stopwatch.StartNew();
        if (n > 0)
            QuickSort(arr, 0, n - 1);
        sw.Stop();

        Console.WriteLine("\nSorted array:");
        for (int i = 0; i < n; i++)
            Console.Write(arr[i] + " ");
        Console.WriteLine();

        Console.WriteLine("\nExecution time: " + sw.Elapsed.TotalSeconds + " seconds");
    }
}
