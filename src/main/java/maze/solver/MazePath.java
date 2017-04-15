package maze.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import graph.Path;
import graph.model.Vertex;
import maze.util.FileUtil;

public class MazePath extends Path {

    public MazePath(final Vertex resultVertex) {
        super(resultVertex);
    }

    public String createMazeTextWithMarkedPath(final byte[][] textLines) {
        return FileUtil.textToString(createMazeTextWithMarkedPathAsBytes(textLines));
    }

    private byte[][] createMazeTextWithMarkedPathAsBytes(final byte[][] textLines) {

        // create path
        final List<MazeVertex> mazeVertexList = createMazeVertexList(resultVertex);

        // create copy of the maze text
        final int mazeHeight = textLines.length;
        final byte[][] mazeWithPath = new byte[textLines.length][];
        for (int j = 0; j < mazeHeight; ++j) {
            mazeWithPath[j] = Arrays.copyOf(textLines[j], textLines[j].length);
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

        return mazeWithPath;
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
