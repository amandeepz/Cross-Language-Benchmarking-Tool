import java.util.Scanner;

public class Main {

    // Quick Sort driver (in-place)
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    // Partition method
    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // place pivot in correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // Main program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of elements: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. Exiting.");
            sc.close();
            return;
        }
        int n = sc.nextInt();
        if (n < 0) {
            System.out.println("Number of elements must be non-negative.");
            sc.close();
            return;
        }

        int[] arr = new int[n];
        if (n > 0) {
            System.out.println("Enter " + n + " elements (space or newline separated):");
            for (int i = 0; i < n; i++) {
                while (!sc.hasNextInt()) {
                    System.out.println("Please enter an integer:");
                    sc.next();
                }
                arr[i] = sc.nextInt();
            }
        }

        long start = System.nanoTime();
        if (n > 0) quickSort(arr, 0, n - 1);
        long end = System.nanoTime();

        System.out.println("\nSorted array:");
        if (n == 0) {
            System.out.println("(empty)");
        } else {
            for (int x : arr) {
                System.out.print(x + " ");
            }
            System.out.println();
        }

        double timeSeconds = (end - start) / 1e9;
        System.out.printf("\nExecution time: %.9f seconds%n", timeSeconds);

        sc.close();
    }
}
