using System;
using System.Collections.Generic;
using System.Diagnostics;

class Program
{
    const int INF = int.MaxValue;

    // Priority Queue using SortedSet (compatible with older C#)
    class Node : IComparable<Node>
    {
        public int Vertex;
        public int Distance;

        public Node(int v, int d)
        {
            Vertex = v;
            Distance = d;
        }

        public int CompareTo(Node other)
        {
            if (Distance != other.Distance)
                return Distance.CompareTo(other.Distance);
            return Vertex.CompareTo(other.Vertex);
        }
    }

    static void Dijkstra(List<Tuple<int, int>>[] graph, int source)
    {
        int n = graph.Length;
        int[] dist = new int[n];
        for (int i = 0; i < n; i++)
            dist[i] = INF;

        SortedSet<Node> pq = new SortedSet<Node>();
        dist[source] = 0;
        pq.Add(new Node(source, 0));

        while (pq.Count > 0)
        {
            Node node = pq.Min;
            pq.Remove(node);

            int u = node.Vertex;

            foreach (var edge in graph[u])
            {
                int v = edge.Item1;
                int weight = edge.Item2;

                if (dist[u] != INF && dist[u] + weight < dist[v])
                {
                    // Remove old entry if exists
                    pq.Remove(new Node(v, dist[v]));

                    dist[v] = dist[u] + weight;
                    pq.Add(new Node(v, dist[v]));
                }
            }
        }

        Console.WriteLine("\nShortest distances from source:");
        for (int i = 0; i < n; i++)
        {
            if (dist[i] == INF)
                Console.WriteLine("Node " + i + ": unreachable");
            else
                Console.WriteLine("Node " + i + ": " + dist[i]);
        }
    }

    static void Main()
    {
        Console.Write("Enter number of vertices and edges: ");
        string[] firstLine = Console.ReadLine().Split();
        int n = int.Parse(firstLine[0]);
        int m = int.Parse(firstLine[1]);

        List<Tuple<int, int>>[] graph = new List<Tuple<int, int>>[n];
        for (int i = 0; i < n; i++)
            graph[i] = new List<Tuple<int, int>>();

        Console.WriteLine("Enter edges (u v weight) [0-based indexing]:");
        for (int i = 0; i < m; i++)
        {
            string[] parts = Console.ReadLine().Split();
            int u = int.Parse(parts[0]);
            int v = int.Parse(parts[1]);
            int w = int.Parse(parts[2]);

            graph[u].Add(Tuple.Create(v, w));
            // For undirected graph, uncomment:
            // graph[v].Add(Tuple.Create(u, w));
        }

        Console.Write("Enter source vertex: ");
        int source = int.Parse(Console.ReadLine());

        Stopwatch sw = Stopwatch.StartNew();
        Dijkstra(graph, source);
        sw.Stop();

        Console.WriteLine("\nExecution time: " + sw.Elapsed.TotalSeconds + " seconds");
    }
}
