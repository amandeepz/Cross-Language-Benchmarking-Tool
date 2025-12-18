use std::io::{self, Read};
use std::time::Instant;

fn main() {
    // Read all input at once
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let mut it = input.split_whitespace();

    println!("Enter rows and columns of Matrix A:");
    let r1: usize = match it.next() {
        Some(v) => v.parse().unwrap(),
        None => return,
    };
    let c1: usize = it.next().unwrap().parse().unwrap();

    println!("Enter rows and columns of Matrix B:");
    let r2: usize = it.next().unwrap().parse().unwrap();
    let c2: usize = it.next().unwrap().parse().unwrap();

    if c1 != r2 {
        eprintln!("Matrix multiplication not possible (columns of A must equal rows of B).");
        return;
    }

    let mut a = vec![vec![0_i64; c1]; r1];
    let mut b = vec![vec![0_i64; c2]; r2];
    let mut c = vec![vec![0_i64; c2]; r1];

    println!("Enter elements of Matrix A (row-wise):");
    for i in 0..r1 {
        for j in 0..c1 {
            a[i][j] = it.next().unwrap().parse().unwrap();
        }
    }

    println!("Enter elements of Matrix B (row-wise):");
    for i in 0..r2 {
        for j in 0..c2 {
            b[i][j] = it.next().unwrap().parse().unwrap();
        }
    }

    let start = Instant::now();

    // Matrix multiplication (i-k-j order for better cache locality)
    for i in 0..r1 {
        for k in 0..c1 {
            let aik = a[i][k];
            for j in 0..c2 {
                c[i][j] += aik * b[k][j];
            }
        }
    }

    let duration = start.elapsed();

    println!("\nResultant Matrix (A x B):");
    for i in 0..r1 {
        for j in 0..c2 {
            print!("{} ", c[i][j]);
        }
        println!();
    }

    println!(
        "\nExecution time: {:.9} seconds",
        duration.as_secs_f64()
    );
}
