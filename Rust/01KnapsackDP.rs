use std::io::{self, Read};
use std::time::Instant;

fn knapsack_01(weights: &Vec<usize>, values: &Vec<i64>, capacity: usize) -> i64 {
    let mut dp = vec![0_i64; capacity + 1];

    for i in 0..weights.len() {
        let w = weights[i];
        let v = values[i];
        // Traverse backwards to ensure 0/1 behavior
        for c in (w..=capacity).rev() {
            let candidate = dp[c - w] + v;
            if candidate > dp[c] {
                dp[c] = candidate;
            }
        }
    }
    dp[capacity]
}

fn main() {
    // Read all input
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let mut it = input.split_whitespace();

    println!("Enter number of items:");
    let n: usize = match it.next() {
        Some(v) => v.parse().unwrap(),
        None => return,
    };

    let mut weights = Vec::with_capacity(n);
    let mut values = Vec::with_capacity(n);

    println!("Enter items as: <weight> <value>:");
    for _ in 0..n {
        let w: usize = it.next().unwrap().parse().unwrap();
        let v: i64 = it.next().unwrap().parse().unwrap();
        weights.push(w);
        values.push(v);
    }

    println!("Enter knapsack capacity:");
    let capacity: usize = it.next().unwrap().parse().unwrap();

    let start = Instant::now();
    let max_value = knapsack_01(&weights, &values, capacity);
    let duration = start.elapsed();

    println!("\nMaximum value achievable: {}", max_value);
    println!(
        "Execution time: {:.9} seconds",
        duration.as_secs_f64()
    );
}
