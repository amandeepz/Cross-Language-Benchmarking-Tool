using System;
using System.Diagnostics;

class Program
{
    // Remove leading zeros
    static string TrimZeros(string s)
    {
        int i = 0;
        while (i < s.Length - 1 && s[i] == '0') i++;
        return s.Substring(i);
    }

    // Add two non-negative numeric strings
    static string AddStrings(string a, string b)
    {
        int i = a.Length - 1, j = b.Length - 1, carry = 0;
        string result = "";

        while (i >= 0 || j >= 0 || carry > 0)
        {
            int da = (i >= 0) ? a[i--] - '0' : 0;
            int db = (j >= 0) ? b[j--] - '0' : 0;
            int sum = da + db + carry;
            carry = sum / 10;
            result = (sum % 10) + result;
        }
        return TrimZeros(result);
    }

    // Subtract b from a (assumes a >= b)
    static string SubtractStrings(string a, string b)
    {
        int i = a.Length - 1, j = b.Length - 1, borrow = 0;
        string result = "";

        while (i >= 0)
        {
            int da = a[i--] - '0';
            int db = (j >= 0) ? b[j--] - '0' : 0;
            int diff = da - db - borrow;
            if (diff < 0)
            {
                diff += 10;
                borrow = 1;
            }
            else
                borrow = 0;

            result = diff + result;
        }
        return TrimZeros(result);
    }

    // Multiply by 10^n
    static string ShiftLeft(string s, int n)
    {
        for (int i = 0; i < n; i++)
            s += "0";
        return s;
    }

    // Karatsuba multiplication
    static string Karatsuba(string x, string y)
    {
        x = TrimZeros(x);
        y = TrimZeros(y);

        // Base case (small numbers)
        if (x.Length < 10 && y.Length < 10)
        {
            long a = long.Parse(x);
            long b = long.Parse(y);
            return (a * b).ToString();
        }

        int n = Math.Max(x.Length, y.Length);
        if (n % 2 != 0) n++;

        x = x.PadLeft(n, '0');
        y = y.PadLeft(n, '0');

        int half = n / 2;

        string aL = x.Substring(0, n - half);
        string aR = x.Substring(n - half);
        string bL = y.Substring(0, n - half);
        string bR = y.Substring(n - half);

        string p1 = Karatsuba(aL, bL);
        string p2 = Karatsuba(aR, bR);
        string p3 = Karatsuba(
            AddStrings(aL, aR),
            AddStrings(bL, bR)
        );

        string mid = SubtractStrings(SubtractStrings(p3, p1), p2);

        return TrimZeros(
            AddStrings(
                AddStrings(ShiftLeft(p1, 2 * half), ShiftLeft(mid, half)),
                p2
            )
        );
    }

    static void Main()
    {
        Console.WriteLine("Karatsuba Multiplication Benchmark");

        Console.Write("Enter first large integer: ");
        string x = Console.ReadLine();

        Console.Write("Enter second large integer: ");
        string y = Console.ReadLine();

        Stopwatch sw = Stopwatch.StartNew();
        string result = Karatsuba(x, y);
        sw.Stop();

        Console.WriteLine("\nProduct:");
        Console.WriteLine(result);

        Console.WriteLine("\nExecution time: " + sw.Elapsed.TotalSeconds + " seconds");
    }
}
