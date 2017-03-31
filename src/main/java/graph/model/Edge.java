package graph.model;

public class Edge {
    final String id;
    final Vertex to;

    public Edge(final String id, final Vertex to) {
        this.id = id;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public Vertex getTo() {
        return to;
    }

    public String toString() {
        return id;
    }
}
