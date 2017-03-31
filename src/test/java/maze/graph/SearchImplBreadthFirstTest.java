package maze.graph;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;

import graph.Path;
import graph.SearchImplBreadthFirst;
import graph.model.Edge;
import graph.model.Vertex;

public class SearchImplBreadthFirstTest {

    private static final Logger LOG = Logger.getLogger(SearchImplBreadthFirstTest.class);

    final SearchImplBreadthFirst searchImplBreadthFirst = new SearchImplBreadthFirst();

    @Test
    public void test() {

        // Creates 4 Vertex graph:
        // v1
        // |
        // v2
        // / \
        // v3a v3b

        final Vertex v1 = new Vertex("v1");
        final Vertex v2 = new Vertex("v2");
        final Vertex v3a = new Vertex("v3a");
        final Vertex v3b = new Vertex("v3b");

        connectVertices(v1, v2, "down", "up");
        connectVertices(v2, v3a, "sw", "ne");
        connectVertices(v2, v3b, "se", "nw");

        final Vertex resultVertex = searchImplBreadthFirst.search(v1, v3b);
        assertEquals(v3b, resultVertex);
        assertEquals(v3b.getParent(), v2);
        assertEquals(v2.getParent(), v1);

        final Path path = new Path(resultVertex);
        LOG.info("path=" + path);
    }

    private void connectVertices(final Vertex from, final Vertex to, final String direction,
            final String backDirection) {
        from.addEdge(new Edge(direction, to));
        to.addEdge(new Edge(backDirection, from));
    }
}
