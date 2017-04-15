package maze.solver;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graph.model.Edge;
import graph.model.Vertex;

public class MazeGraph {

    private static final Logger LOG = LoggerFactory.getLogger(MazeGraph.class);

    final Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();

    private String createVertexId(final int i, final int j) {
        final StringBuffer sb = new StringBuffer();
        sb.append(i);
        sb.append(",");
        sb.append(j);
        return sb.toString();
    }

    private void connectVertices(final Vertex from, final Vertex to, final String direction) {
        from.addEdge(new Edge(direction, to));
    }

    public Vertex getVertexByLocation(final int i, final int j) {
        return vertexMap.get(createVertexId(i, j));
    }

    public void buildGraph(final byte[][] textLines) {
        final int expectedLineLength = textLines[0].length;
        LOG.debug("expectedLineLength={}", expectedLineLength);
        byte[] prevLine = null;
        for (int j = 0; j < textLines.length; ++j) {
            final byte[] line = textLines[j];
            LOG.trace("line[{}]=\"{}\"", j, line);
            if (line.length != expectedLineLength) {
                throw new IllegalStateException();
            }
            for (int i = 0; i < line.length; ++i) {
                final char charAtI = (char) line[i];
                if (charAtI == ' ') {
                    final String newVertexId = createVertexId(i, j);
                    LOG.debug("adding vertex {}", newVertexId);
                    final Vertex newVertex = new MazeVertex(newVertexId, i, j);
                    if (i > 0) {
                        final char charAtIMinus1 = (char) line[i - 1];
                        if (charAtIMinus1 == ' ') {
                            final String westernVertexId = createVertexId(i - 1, j);
                            final Vertex westernVertex = vertexMap.get(westernVertexId);
                            if (westernVertex == null) {
                                throw new IllegalStateException();
                            }
                            LOG.debug("connecting vertices: {} <EW> {}", newVertexId, westernVertexId);
                            connectVertices(westernVertex, newVertex, "E");
                            connectVertices(newVertex, westernVertex, "W");
                        }
                    }
                    if (j > 0) {
                        if (prevLine == null) {
                            throw new IllegalStateException();
                        }
                        final char charAtJMinus1 = (char) prevLine[i];
                        if (charAtJMinus1 == ' ') {
                            final String northernVertexId = createVertexId(i, j - 1);
                            final Vertex northernVertex = vertexMap.get(northernVertexId);
                            if (northernVertex == null) {
                                throw new IllegalStateException();
                            }
                            LOG.debug("connecting vertices: {} <NS> {}", newVertexId, northernVertexId);
                            connectVertices(northernVertex, newVertex, "S");
                            connectVertices(newVertex, northernVertex, "N");
                        }
                    }
                    vertexMap.put(newVertexId, newVertex);
                }
            }
            prevLine = line;
        }
    }
}
