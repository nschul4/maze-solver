package graph;

import graph.model.Edge;
import graph.model.Vertex;

public class Path {
    protected final Vertex resultVertex;

    public Path(final Vertex resultVertex) {
        this.resultVertex = resultVertex;
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        Vertex v = resultVertex;
        while (v != null) {
            sb.append(v.getId());
            final Vertex prev = v;
            v = v.getParent();
            if (prev != null && v != null) {
                final Edge e = prev.getEdgeTo(v);
                sb.append(' ');
                sb.append(e);
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
