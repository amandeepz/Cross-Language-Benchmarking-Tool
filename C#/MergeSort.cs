using System;
using System.Diagnostics;

class Program
{
    // Merge two sorted subarrays
    static void Merge(int[] arr, int left, int mid, int right)
    {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int iIndex = 0, jIndex = 0, k = left;

        while (iIndex < n1 && jIndex < n2)
        {
            if (L[iIndex] <= R[jIndex])
                arr[k++] = L[iIndex++];
            else
                arr[k++] = R[jIndex++];
        }

        while (iIndex < n1)
            arr[k++] = L[iIndex++];

        while (jIndex < n2)
            arr[k++] = R[jIndex++];
    }

    // Merge Sort recursive function
    static void MergeSort(int[] arr, int left, int right)
    {
        if (left < right)
        {
            int mid = left + (right - left) / 2;
            MergeSort(arr, left, mid);
            MergeSort(arr, mid + 1, right);
            Merge(arr, left, mid, right);
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
            MergeSort(arr, 0, n - 1);
        sw.Stop();

        Console.WriteLine("\nSorted array:");
        for (int i = 0; i < n; i++)
            Console.Write(arr[i] + " ");
        Console.WriteLine();

        Console.WriteLine("\nExecution time: " + sw.Elapsed.TotalSeconds + " seconds");
    }
}
