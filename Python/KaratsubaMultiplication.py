import time

# Remove leading zeros
def trim(s):
    s = s.lstrip("0")
    return s if s != "" else "0"

# Add two numeric strings
def add_str(a, b):
    a = a[::-1]
    b = b[::-1]
    carry = 0
    result = []

    for i in range(max(len(a), len(b))):
        da = int(a[i]) if i < len(a) else 0
        db = int(b[i]) if i < len(b) else 0
        s = da + db + carry
        result.append(str(s % 10))
        carry = s // 10

    if carry:
        result.append(str(carry))

    return trim("".join(result[::-1]))

# Subtract b from a (assuming a >= b)
def sub_str(a, b):
    a = a[::-1]
    b = b[::-1]
    result = []
    borrow = 0

    for i in range(len(a)):
        da = int(a[i])
        db = int(b[i]) if i < len(b) else 0
        d = da - db - borrow
        if d < 0:
            d += 10
            borrow = 1
        else:
            borrow = 0
        result.append(str(d))

    return trim("".join(result[::-1]))

# Multiply by 10^n (just append n zeros)
def shift(s, n):
    return s + "0" * n

# Standard integer multiplication for small values
def small_mul(a, b):
    return str(int(a) * int(b))

# Karatsuba multiplication
def karatsuba(a, b):
    a = trim(a)
    b = trim(b)

    # Base case: small enough for direct multiply
    if len(a) < 10 and len(b) < 10:
        return small_mul(a, b)

    # Normalize length to be equal and even
    n = max(len(a), len(b))
    if n % 2 == 1:
        n += 1

    a = a.zfill(n)
    b = b.zfill(n)
    half = n // 2

    aL, aR = a[:n-half], a[n-half:]
    bL, bR = b[:n-half], b[n-half:]

    # Recursive calls
    p1 = karatsuba(aL, bL)
    p2 = karatsuba(aR, bR)
    p3 = karatsuba(add_str(aL, aR), add_str(bL, bR))

    # Combine Karatsuba results:
    # result = p1*10^(2*half) + (p3 - p1 - p2)*10^(half) + p2
    mid = sub_str(sub_str(p3, p1), p2)

    result = add_str(
        add_str(shift(p1, 2 * half), shift(mid, half)),
        p2
    )
    return trim(result)

# ---------------------------- MAIN PROGRAM ----------------------------
if __name__ == "__main__":
    print("Karatsuba Multiplication Benchmark")

    x = input("Enter first large integer: ").strip()
    y = input("Enter second large integer: ").strip()

    start = time.perf_counter()
    result = karatsuba(x, y)
    end = time.perf_counter()

    print("\nProduct:")
    print(result)
    
    print("\nExecution time:", end - start, "seconds")
