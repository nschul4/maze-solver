package maze.generator;

import maze.generator.impl.MazeGeneratorImpl;

public class Main {
    public static void main(final String[] args) {
        final MazeGenerator mg = new MazeGeneratorImpl(8, 8);
        mg.generate();
        System.out.println(mg);
    }
}
