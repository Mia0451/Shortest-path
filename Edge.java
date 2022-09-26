public class Edge {

    Vertex first;
    Vertex second;

    float length;

    public Edge(Vertex f, Vertex s, float l) {
        first = f;
        second = s;
        length = l;
    }
}
