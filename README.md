**🚀 Cross-Language Benchmarking Tool**

A comprehensive cross-language performance benchmarking framework designed to compare execution efficiency of commonly used yet computationally complex algorithms across multiple programming languages.

This project evaluates algorithmic behavior, runtime performance, and language-level execution characteristics under consistent workloads, making it useful for systems design understanding, performance engineering, and language comparison.

**Motivation**

Modern software systems are built using multiple programming languages, each with different runtime models, memory management strategies, and optimization capabilities.
This project aims to:

Quantitatively compare algorithm performance across languages
Understand how runtime environments affect execution time
Build intuition around language choice for performance-critical systems
Demonstrate strong algorithmic and systems-level engineering skills

**🧠 Algorithms Benchmarked**

The following 6 widely-used and computationally intensive algorithms were selected:

**Algorithm	Category**  
Merge Sort	- Divide & Conquer  
Quick Sort	- Divide & Conquer  
Matrix Multiplication	- Linear Algebra  
Dijkstra’s Algorithm	- Graph Algorithms  
0/1 Knapsack (DP)	- Dynamic Programming  
Karatsuba Multiplication	- Advanced Arithmetic  

Each algorithm was implemented using the same logical design pattern in every language to ensure fairness.

**🛠️ Languages & Tech Stack**

Languages

C++  
Python  
Java  
C#  
Rust  

**Core Technologies & Concepts**

STL / Standard Libraries  
JVM & .NET Runtime  
Rust Ownership & Memory Safety  
High-resolution timing APIs:  
  std::chrono (C++)  
  time.perf_counter() (Python)  
  System.nanoTime() (Java)  
  Stopwatch (C#)  
  std::time::Instant (Rust)  
Algorithmic optimization & complexity analysis  

**📊 Benchmark Results**
_⏱ Execution Time Comparison (in seconds)_

**C++**

Merge Sort: 0.00001175  
Quick Sort: 0.000001231  
Matrix Multiplication: 0.00000079  
Dijkstra: 0.000001718  
0/1 Knapsack DP: 0.000004012  
Karatsuba Multiplication: 0.000017366  

**Python**

Merge Sort: 0.000018667  
Quick Sort: 0.000009734  
Matrix Multiplication: 0.000008861  
Dijkstra: 0.000171226  
0/1 Knapsack DP: 0.000014480  
Karatsuba Multiplication: 0.000399020  

**Java**

Quick Sort: 0.000007004  
Merge Sort: 0.000011712  
Dijkstra: 0.000603142  
Matrix Multiplication: 0.000001378  
0/1 Knapsack DP: 0.000082493  
Karatsuba Multiplication: 0.022068936  

**C#**

Merge Sort: 0.000238400  
Quick Sort: 0.000155900  
Dijkstra: 0.004863800  
Matrix Multiplication: 0.000045300  
0/1 Knapsack DP: 0.000043200  
Karatsuba Multiplication: 0.006018500  

**Rust**

Merge Sort: 0.000001589  
Quick Sort: 0.000000503  
Dijkstra: 0.000001146  
Matrix Multiplication: 0.000000636  
0/1 Knapsack DP: 0.000000777  
Karatsuba Multiplication: 0.000014516  

**🔍 Key Observations**

Rust and C++ consistently deliver the fastest execution due to low-level control and minimal runtime overhead.  
Python shows higher execution times due to interpreter overhead.  
Java and C# perform competitively but incur managed-runtime costs.  
Karatsuba multiplication highlights recursion and string-handling overhead differences across languages.  
