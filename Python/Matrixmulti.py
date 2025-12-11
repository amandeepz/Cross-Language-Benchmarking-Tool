import time

# Matrix Multiplication (A x B)
def matrix_multiply(A, B):
    r1 = len(A)
    c1 = len(A[0])
    r2 = len(B)
    c2 = len(B[0])

    # Initialize result matrix C with zeros
    C = [[0] * c2 for _ in range(r1)]

    # Multiply
    for i in range(r1):
        for k in range(c1):
            for j in range(c2):
                C[i][j] += A[i][k] * B[k][j]

    return C


# ---------------------------- MAIN PROGRAM ----------------------------
if __name__ == "__main__":
    print("Matrix Multiplication Benchmark (A x B)")

    # Read dimensions
    r1, c1 = map(int, input("Enter rows and columns of Matrix A: ").split())
    r2, c2 = map(int, input("Enter rows and columns of Matrix B: ").split())

    if c1 != r2:
        print("Error: Cannot multiply. Columns of A must equal rows of B.")
        exit(1)

    print("\nEnter elements of Matrix A row-wise:")
    A = []
    for _ in range(r1):
        row = list(map(int, input().split()))
        A.append(row)

    print("\nEnter elements of Matrix B row-wise:")
    B = []
    for _ in range(r2):
        row = list(map(int, input().split()))
        B.append(row)

    start_time = time.perf_counter()
    C = matrix_multiply(A, B)
    end_time = time.perf_counter()

    print("\nResultant Matrix (A x B):")
    for row in C:
        print(*row)

    print("\nExecution time:", end_time - start_time, "seconds")
