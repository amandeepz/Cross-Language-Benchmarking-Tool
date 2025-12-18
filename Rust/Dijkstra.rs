use std::cmp::Reverse;
use std::collections::BinaryHeap;
use std::io::{self, Read};
use std::time::Instant;

const INF: i64 = i64::MAX / 4;

fn dijkstra(adj: &Vec<Vec<(usize, i64)>>, src: usize) -> Vec<i64> {
    let n = adj.len();
    let mut dist = vec![INF; n];
    let mut heap = BinaryHeap::new();

    dist[src] = 0;
    heap.push(Reverse((0_i64, src)));

    while let Some(Reverse((d, u))) = heap.pop() {
        if d != dist[u] {
            continue; // stale entry
        }
        for &(v, w) in &adj[u] {
            let nd = d + w;
            if nd < dist[v] {
                dist[v] = nd;
                heap.push(Reverse((nd, v)));
            }
        }
    }

    dist
}

fn main() {
    // Read all input
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let mut it = input.split_whitespace();

    println!("Enter number of vertices and edges:");
    let n: usize = match it.next() {
        Some(v) => v.parse().unwrap(),
        None => return,
    };
    let m: usize = it.next().unwrap().parse().unwrap();

    let mut adj: Vec<Vec<(usize, i64)>> = vec![Vec::new(); n];

    println!("Enter edges (u v weight) [0-based indexing]:");
    for _ in 0..m {
        let u: usize = it.next().unwrap().parse().unwrap();
        let v: usize = it.next().unwrap().parse().unwrap();
        let w: i64 = it.next().unwrap().parse().unwrap();
        adj[u].push((v, w));
        // For undirected graph, also add:
        // adj[v].push((u, w));
    }

    println!("Enter source vertex:");
    let src: usize = it.next().unwrap().parse().unwrap();

    let start = Instant::now();
    let dist = dijkstra(&adj, src);
    let duration = start.elapsed();

    println!("\nShortest distances from source {}:", src);
    for i in 0..n {
        if dist[i] == INF {
            println!("Node {}: unreachable", i);
        } else {
            println!("Node {}: {}", i, dist[i]);
        }
    }

    println!(
        "\nExecution time: {:.9} seconds",
        duration.as_secs_f64()
    );
}
