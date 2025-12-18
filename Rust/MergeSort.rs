use std::io::{self, Read};
use std::time::Instant;

// Merge two sorted slices
fn merge(left: &[i32], right: &[i32]) -> Vec<i32> {
    let mut result = Vec::with_capacity(left.len() + right.len());
    let mut i = 0;
    let mut j = 0;

    while i < left.len() && j < right.len() {
        if left[i] <= right[j] {
            result.push(left[i]);
            i += 1;
        } else {
            result.push(right[j]);
            j += 1;
        }
    }

    // Append remaining elements
    result.extend_from_slice(&left[i..]);
    result.extend_from_slice(&right[j..]);

    result
}

// Merge sort function
fn merge_sort(arr: &[i32]) -> Vec<i32> {
    if arr.len() <= 1 {
        return arr.to_vec();
    }

    let mid = arr.len() / 2;
    let left = merge_sort(&arr[..mid]);
    let right = merge_sort(&arr[mid..]);

    merge(&left, &right)
}

fn main() {
    // Read all input at once (fast and simple)
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let mut iter = input.split_whitespace();

    println!("Enter number of elements:");
    let n: usize = match iter.next() {
        Some(v) => v.parse().unwrap(),
        None => return,
    };

    println!("Enter elements separated by space:");
    let mut arr = Vec::with_capacity(n);
    for _ in 0..n {
        if let Some(v) = iter.next() {
            arr.push(v.parse::<i32>().unwrap());
        }
    }

    let start = Instant::now();
    let sorted = merge_sort(&arr);
    let duration = start.elapsed();

    println!("\nSorted array:");
    for x in &sorted {
        print!("{} ", x);
    }
    println!();

    println!(
        "\nExecution time: {:.9} seconds",
        duration.as_secs_f64()
    );
}
