package maze.solver;

import graph.model.Vertex;

public class MazeVertex extends Vertex {
    private final int i;
    private final int j;

    public MazeVertex(final String id, final int i, final int j) {
        super(id);
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
