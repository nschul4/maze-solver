package graph.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Vertex {
    private final String id;
    private final Set<Edge> edgeSet = new HashSet<Edge>();

    private Vertex parent;

    public Vertex(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addEdge(final Edge edge) {
        edgeSet.add(edge);
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(final Vertex parent) {
        this.parent = parent;
    }

    public List<Vertex> getAdjacentVertices() {
        final List<Vertex> adjacentVerticesList = new ArrayList<Vertex>();
        for (final Edge edge : edgeSet) {
            adjacentVerticesList.add(edge.getTo());
        }
        return adjacentVerticesList;
    }

    public Edge getEdgeTo(final Vertex v) {
        for (final Edge e : edgeSet) {
            if (e.getTo() == v) {
                return e;
            }
        }
        return null;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("v");
        sb.append(id);
        sb.append("{");
        final Iterator<Edge> it = edgeSet.iterator();
        while (it.hasNext()) {
            final Edge e = it.next();
            sb.append("e");
            sb.append(e.getId());
            sb.append(">");
            sb.append("v");
            sb.append(e.getTo().getId());
            if (it.hasNext()) {
                sb.append("|");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
