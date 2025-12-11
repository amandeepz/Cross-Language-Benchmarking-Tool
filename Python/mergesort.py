import time

# Merge function
def merge(left, right):
    merged = []
    i = j = 0

    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            merged.append(left[i])
            i += 1
        else:
            merged.append(right[j])
            j += 1

    # Append remaining elements
    merged.extend(left[i:])
    merged.extend(right[j:])
    return merged

# Merge Sort function
def merge_sort(arr):
    if len(arr) <= 1:
        return arr

    mid = len(arr) // 2
    left = merge_sort(arr[:mid])
    right = merge_sort(arr[mid:])
    return merge(left, right)


# ---------------------------- MAIN PROGRAM ----------------------------
if __name__ == "__main__":
    n = int(input("Enter number of elements: "))
    print("Enter elements separated by space:")
    arr = list(map(int, input().split()))

    start_time = time.perf_counter()
    sorted_arr = merge_sort(arr)
    end_time = time.perf_counter()

    print("\nSorted array:")
    print(sorted_arr)

    print("\nExecution time:", end_time - start_time, "seconds")
