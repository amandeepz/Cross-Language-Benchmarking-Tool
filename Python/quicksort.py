import time

# Quick Sort 
def quick_sort(arr, low, high):
    if low < high:
        p = partition(arr, low, high)
        quick_sort(arr, low, p - 1)
        quick_sort(arr, p + 1, high)

def partition(arr, low, high):
    pivot = arr[high]   # last element as pivot
    i = low - 1

    for j in range(low, high):
        if arr[j] <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]

    # place pivot in correct spot
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1


# ---------------------------- MAIN PROGRAM ----------------------------
if __name__ == "__main__":
    n = int(input("Enter number of elements: "))
    print("Enter elements separated by space:")
    arr = list(map(int, input().split()))

    start_time = time.perf_counter()
    quick_sort(arr, 0, n - 1)
    end_time = time.perf_counter()

    print("\nSorted array:")
    print(arr)

    print("\nExecution time:", end_time - start_time, "seconds")
