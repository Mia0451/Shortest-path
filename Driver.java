import java.util.Scanner;
import java.util.StringTokenizer;

public class Driver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please input the number of vertices: ");
        int numOfVertices = scanner.nextInt();

        System.out.printf("Please input %d airports, one per line: \n", numOfVertices);
        String[] airports = new String[numOfVertices];
        for (int i = 0; i < numOfVertices; ++i) {
            String s = scanner.nextLine();
            if (!s.isEmpty()) {
                airports[i] = s;
            } else {
                --i;
            }
        }

        System.out.println("Please input edges between those airports, and " +
                "their distance, ex \"SFO LAX 337\". One edge per line, after done, type word \"done\"");
        DLinkedList<Edge>[] graph = new DLinkedList[numOfVertices];
        for (int i = 0; i < airports.length; ++i) {
            graph[i] = new DLinkedList<>();
        }
        String edge = scanner.nextLine();
        while(!edge.equals("done")) {
            if (edge.isEmpty()) {
                edge = scanner.nextLine();
                continue;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(edge);
            Vertex<String> airport1 = new Vertex<>(stringTokenizer.nextToken());
            Vertex<String> airport2 = new Vertex<>(stringTokenizer.nextToken());
            float distance = Float.parseFloat(stringTokenizer.nextToken());
            // un-direct graph, the edge should be added to the related two vertices.
            addEdgeToGraph(airports, graph, new Edge(airport1, airport2, distance));
            addEdgeToGraph(airports, graph, new Edge(airport2, airport1, distance));
            edge = scanner.nextLine();
        }

        shortestPath(airports, graph);
    }


    private static void shortestPath(String[] airports, DLinkedList<Edge>[] graph) {
        int[] bestAirportPath = new int[airports.length];
        float[] bestShortestPath = new float[]{Float.MAX_VALUE};

        // Any airport could be the start point.
        for (int start = 0; start < airports.length; ++start) {

            // used to indicate whether an airport already be included into the path or not.
            boolean[] visited = new boolean[airports.length];
            // the visited airports
            int[] airportPath = new int[airports.length];
            visited[start] = true;
            airportPath[0] = start;

            dfs(airports, graph, visited, airportPath, start, 1, 0f, bestAirportPath, bestShortestPath);
        }


        if (bestShortestPath[0] != Float.MAX_VALUE) {
            // Now print the path.
            System.out.println("The airports in the shortest path are: ");
            for (int i = 0; i < bestAirportPath.length; ++i) {
                System.out.print((i==0?"":"->") + airports[bestAirportPath[i]]);
            }
            System.out.println("\nAnd the shortest path is " + bestShortestPath[0]);
        } else {
            System.out.println("Cannot find a path that visited each airport once.");
        }
    }

    private static void dfs(String[] airports, DLinkedList<Edge>[] graph,
                            boolean[] visited, int[] airportPath, int currentAirport, int numOfVisitedAirports,
                            float currentLength, int[] bestAirportPath, float[] bestShortestPath) {
        if (numOfVisitedAirports == airports.length) {
            if (currentLength < bestShortestPath[0]) {
                bestShortestPath[0] = currentLength;
                for (int i = 0; i < bestAirportPath.length; ++i) {
                    bestAirportPath[i] = airportPath[i];
                }
            }
            return;
        }

        for (int i = 0; i < graph[currentAirport].Count(); ++i) {
            Edge e = graph[currentAirport].get(i);
            int ind = getIndex(airports, (String)e.second.data);
            if (!visited[ind]) {
                visited[ind] = true;
                airportPath[numOfVisitedAirports] = ind;
                dfs(airports, graph, visited, airportPath, ind, numOfVisitedAirports+1, currentLength + e.length, bestAirportPath, bestShortestPath);
                visited[ind] = false;
            }
        }
    }

    private static void addEdgeToGraph(String[] airports, DLinkedList<Edge>[] graph, Edge newEdge) {
        for (int i = 0; i < airports.length; ++i) {
            if (newEdge.first.data.equals(airports[i])) {
                graph[i].Append(newEdge);
                break;
            }
        }
    }

    private static int getIndex(String[] airports, String airport) {
        for (int i = 0; i < airports.length; ++i) {
            if (airport.equals(airports[i])) {
                return i;
            }
        }
        return -1;
    }
}
