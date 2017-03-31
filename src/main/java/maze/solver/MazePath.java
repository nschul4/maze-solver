package maze.solver;

import java.util.ArrayList;
import java.util.List;

import graph.Path;
import graph.model.Vertex;

public class MazePath extends Path {

    public MazePath(final Vertex resultVertex) {
        super(resultVertex);
    }

    public String createMazeTextWithMarkedPath(final String[] textLines) {

        // create path
        final List<MazeVertex> mazeVertexList = createMazeVertexList(resultVertex);

        // create modifiable byte representation of maze lines
        final int mazeHeight = textLines.length;
        final byte[][] mazeWithPath = new byte[mazeHeight][];
        for (int j = 0; j < mazeHeight; ++j) {
            mazeWithPath[j] = textLines[j].getBytes();
        }

        // mark path
        for (final MazeVertex mazeVertex : mazeVertexList) {
            mazeWithPath[mazeVertex.getJ()][mazeVertex.getI()] = '+';
        }

        // mark start and end of path
        final int trackPathListLength = mazeVertexList.size();
        mazeWithPath[mazeVertexList.get(0).getJ()][mazeVertexList.get(0).getI()] = 'S';
        mazeWithPath[mazeVertexList.get(trackPathListLength - 1).getJ()][mazeVertexList.get(trackPathListLength - 1)
                .getI()] = 'E';

        // convert bytes back to strings
        final StringBuffer sb = new StringBuffer();
        for (int j = 0; j < mazeHeight; ++j) {
            sb.append(new String(mazeWithPath[j]));
            if (j < mazeHeight - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private List<MazeVertex> createMazeVertexList(final Vertex resultVertex) {
        final List<MazeVertex> trackPathList = new ArrayList<MazeVertex>();
        Vertex v = resultVertex;
        while (v != null) {
            trackPathList.add((MazeVertex) v);
            v = v.getParent();
        }
        return trackPathList;
    }
}
