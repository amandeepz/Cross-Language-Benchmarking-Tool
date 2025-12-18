use std::io::{self, Read};
use std::time::Instant;

// Remove leading zeros
fn trim(s: &str) -> String {
    let t = s.trim_start_matches('0');
    if t.is_empty() { "0".to_string() } else { t.to_string() }
}

// Add two non-negative numeric strings
fn add_str(a: &str, b: &str) -> String {
    let mut i = a.len() as i32 - 1;
    let mut j = b.len() as i32 - 1;
    let mut carry = 0;
    let mut res = Vec::new();

    while i >= 0 || j >= 0 || carry > 0 {
        let da = if i >= 0 { a.as_bytes()[i as usize] - b'0' } else { 0 };
        let db = if j >= 0 { b.as_bytes()[j as usize] - b'0' } else { 0 };
        let sum = da + db + carry;
        res.push((sum % 10) as u8 + b'0');
        carry = sum / 10;
        i -= 1;
        j -= 1;
    }

    res.reverse();
    trim(&String::from_utf8(res).unwrap())
}

// Subtract b from a (assumes a >= b)
fn sub_str(a: &str, b: &str) -> String {
    let mut i = a.len() as i32 - 1;
    let mut j = b.len() as i32 - 1;
    let mut borrow = 0;
    let mut res = Vec::new();

    while i >= 0 {
        let mut da = a.as_bytes()[i as usize] - b'0';
        let db = if j >= 0 { b.as_bytes()[j as usize] - b'0' } else { 0 };
        if da < db + borrow {
            da += 10;
            borrow = 1;
        } else {
            borrow = 0;
        }
        let diff = da - db - borrow;
        res.push(diff + b'0');
        i -= 1;
        j -= 1;
    }

    res.reverse();
    trim(&String::from_utf8(res).unwrap())
}

// Multiply by 10^n (append zeros)
fn shift(s: &str, n: usize) -> String {
    if s == "0" {
        "0".to_string()
    } else {
        let mut r = String::from(s);
        r.extend(std::iter::repeat('0').take(n));
        r
    }
}

// Karatsuba multiplication
fn karatsuba(x: &str, y: &str) -> String {
    let x = trim(x);
    let y = trim(y);

    // Base case: small enough for direct multiplication
    if x.len() < 10 && y.len() < 10 {
        let a: u64 = x.parse().unwrap();
        let b: u64 = y.parse().unwrap();
        return (a * b).to_string();
    }

    let mut n = std::cmp::max(x.len(), y.len());
    if n % 2 != 0 {
        n += 1;
    }

    let x = format!("{:0>width$}", x, width = n);
    let y = format!("{:0>width$}", y, width = n);
    let half = n / 2;

    let (a, b) = x.split_at(n - half);
    let (c, d) = y.split_at(n - half);

    let ac = karatsuba(a, c);
    let bd = karatsuba(b, d);
    let ab_cd = karatsuba(&add_str(a, b), &add_str(c, d));

    let mid = sub_str(&sub_str(&ab_cd, &ac), &bd);

    trim(&add_str(
        &add_str(&shift(&ac, 2 * half), &shift(&mid, half)),
        &bd,
    ))
}

fn main() {
    // Read input
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let mut it = input.split_whitespace();

    println!("Enter first large integer:");
    let x = match it.next() {
        Some(v) => v,
        None => return,
    };

    println!("Enter second large integer:");
    let y = it.next().unwrap();

    let start = Instant::now();
    let result = karatsuba(x, y);
    let duration = start.elapsed();

    println!("\nProduct:");
    println!("{}", result);
    println!(
        "\nExecution time: {:.9} seconds",
        duration.as_secs_f64()
    );
}
