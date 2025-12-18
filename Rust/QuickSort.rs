use std::io::{self, Read};
use std::time::Instant;

// Partition function 
fn partition(arr: &mut [i32], low: isize, high: isize) -> isize {
    let pivot = arr[high as usize];
    let mut i = low - 1;

    for j in low..high {
        if arr[j as usize] <= pivot {
            i += 1;
            arr.swap(i as usize, j as usize);
        }
    }

    arr.swap((i + 1) as usize, high as usize);
    i + 1
}

// Quick Sort recursive function
fn quick_sort(arr: &mut [i32], low: isize, high: isize) {
    if low < high {
        let pi = partition(arr, low, high);
        quick_sort(arr, low, pi - 1);
        quick_sort(arr, pi + 1, high);
    }
}

fn main() {
    // Read all input at once
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let mut iter = input.split_whitespace();

    println!("Enter number of elements:");
    let n: usize = match iter.next() {
        Some(v) => v.parse().unwrap(),
        None => return,
    };

    println!("Enter elements separated by space:");
    let mut arr: Vec<i32> = Vec::with_capacity(n);
    for _ in 0..n {
        if let Some(v) = iter.next() {
            arr.push(v.parse().unwrap());
        }
    }

    let start = Instant::now();
    if n > 0 {
        let len = arr.len();
        quick_sort(&mut arr, 0, (len - 1) as isize);
    }
    let duration = start.elapsed();

    println!("\nSorted array:");
    for x in &arr {
        print!("{} ", x);
    }
    println!();

    println!(
        "\nExecution time: {:.9} seconds",
        duration.as_secs_f64()
    );
}
