package org.example;

import java.util.*;

public class GraphProcessor {
    static Graph graph;

    public GraphProcessor(Graph graph) {
        GraphProcessor.graph = graph;
    }

    public static void dijkstra(int source, int[] costs, int[] parent)  {
        int n = graph.size();
        //System.out.println("n is = " + n);
        PriorityQueue<Node> min = new PriorityQueue<>((x, y) -> x.cost - y.cost);
        boolean[]visited = new boolean[n];
        Arrays.fill(costs,Integer.MAX_VALUE);
        Arrays.fill(visited , false);
        costs[source] = 0 ;
        for(int i = 0 ; i < n ;++i )
            parent[i] = -1 ;
        min.add(new Node(source , 0));
        int[][] matrix = graph.getAdjacencyMatrix();
        while (!min.isEmpty()) {
            int node = min.peek().vertex;
            int weight = min.peek().cost;
            min.poll();
            if (visited[node])
                continue;
            visited[node] = true;
            List<Edge> adj = graph.getAdj(node);
//            System.out.print("vertex is = " + node);
            for(int i = 0 ; i < n ; i++){
                if(matrix[node][i] == 0 || node == i )
                    continue;
//                System.out.print("-> "+   " ");
                int src = node ;
                int des = i ;
                if( !visited[des]&& costs[src] != Integer.MAX_VALUE && costs[des] > costs[src] + matrix[node][i]){
                    costs[des] =  costs[src] + matrix[node][i];
                    parent[des] = src ;
                    min.offer(new Node(des , costs[des]));
                }
            }
//            System.out.println();
        }
//        for(int i = 0 ; i < n ; i++)
//            System.out.println("cost of "+ i + " = " + costs[i]);
    }


    public boolean bellmanFord(int source, int[] costs, int[] parents) {
        int n = graph.Size();
        Arrays.fill(costs, Integer.MAX_VALUE);
         Arrays.fill(parents,-1);
        costs[source] = 0;
        for (int i = 1; i < n; i++) {
            for (Edge edge : graph.getEdges()) {
                int u = edge.getSourceVertex();
                int v = edge.getDestinationVertex();
                int weight = edge.getWeight();

                if (costs[u] != Integer.MAX_VALUE && costs[u] + weight < costs[v]) {
                    costs[v] = costs[u] + weight;
                    parents[v] = u;
                }
            }
        }
        for (Edge edge : graph.getEdges()) {
            int u = edge.getSourceVertex();
            int v = edge.getDestinationVertex();
            int weight = edge.getWeight();
            if (costs[u] != Integer.MAX_VALUE && costs[u] + weight < costs[v]) {
                return false;   //negative cycle detected
            }
        }
        return true;
    }

    public boolean floydWarshall(int[][] costs, int[][] predecessor) {
        int n = graph.getV();
        boolean noCycle = true;

        initializeArrays(costs, predecessor, n);
        populateArrays(costs, predecessor);

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (costs[i][k] != Integer.MAX_VALUE && costs[k][j] != Integer.MAX_VALUE) {
                        if (costs[i][k] + costs[k][j] < costs[i][j]) {
                            costs[i][j] = costs[i][k] + costs[k][j];
                            predecessor[i][j] = predecessor[k][j];
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (costs[i][i] < 0) {
                noCycle = false;
                break;
            }
        }
        return noCycle;
    }

    private void initializeArrays(int[][] costs, int[][] parents, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i != j) {
                    costs[i][j] = Integer.MAX_VALUE;
                }
                else{
                    costs[i][j] = 0;
                }
                parents[i][j] = -1;
            }
        }
    }

    private void populateArrays(int[][] costs, int[][] parents) {
        boolean[][] arr = new boolean[costs.length][costs.length];
        for (Edge e : graph.getEdges()) {
            if(!arr[e.getSourceVertex()][e.getDestinationVertex()]){
                costs[e.getSourceVertex()][e.getDestinationVertex()] = e.getWeight();
                arr[e.getSourceVertex()][e.getDestinationVertex()] = true;
                parents[e.getSourceVertex()][e.getDestinationVertex()] = e.getSourceVertex();
            }
            else{
                if(e.getWeight() < costs[e.getSourceVertex()][e.getDestinationVertex()]){
                    costs[e.getSourceVertex()][e.getDestinationVertex()] = e.getWeight();
                    arr[e.getSourceVertex()][e.getDestinationVertex()] = true;
                    parents[e.getSourceVertex()][e.getDestinationVertex()] = e.getSourceVertex();
                }
            }
        }
    }
}
