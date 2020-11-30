package maze.generator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import maze.generator.MazeGenerator;

public class MazeGeneratorImpl implements MazeGenerator {

    private final int width;
    private final int height;

    // cell (i,j)'s N wall is wallsH[i, j]
    // cell (i,j)'s S wall is wallsH[i, j + 1]
    // cell (i,j)'s W wall is wallsV[i, j]
    // cell (i,j)'s E wall is wallsV[i + 1, j]

    // wall H (i, j) separates cell (i, j) and (i, j - 1)
    // and is both cell (i, j)'s N wall and cell (i, j - 1)'s S wall.
    // wall V (i, j) separates cell (i, j) and (i - 1, j)
    // and is both cell (i, j)'s W wall and cell (i - 1, j)'s E wall.

    private final boolean cells[][];
    private final boolean wallsH[][];
    private final boolean wallsV[][];

    private final Random rand = new Random();

    private final List<Wall> wallList = new ArrayList<Wall>();

    public MazeGeneratorImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
        cells = new boolean[width][height];
        wallsH = new boolean[width][height + 1];
        wallsV = new boolean[width + 1][height];
    }

    private void addCellWallsToList(final int i, final int j) {
        if (j > 0 && !wallsH[i][j]) {
            // add north wall
            wallList.add(new Wall(WallType.HORIZONTAL, i, j));
        }
        if (j < height - 1 && !wallsH[i][j + 1]) {
            // add south wall
            wallList.add(new Wall(WallType.HORIZONTAL, i, j + 1));
        }
        if (i > 0 && !wallsV[i][j]) {
            // add west wall
            wallList.add(new Wall(WallType.VERTICAL, i, j));
        }
        if (i < width - 1 && !wallsV[i + 1][j]) {
            // add east wall
            wallList.add(new Wall(WallType.VERTICAL, i + 1, j));
        }
    }

    public void generate() {

        // Randomized Prim's algorithm
        //
        // https://en.wikipedia.org/wiki/Maze_generation_algorithm
        // 03/27/2017
        //
        // __ 1. Start with a grid full of walls.
        // __ 2. Pick a cell, mark it as part of the maze. Add the walls of the
        // cell to the wall list.
        // __ 3. While there are walls in the list:
        // __ __ b. Pick a random wall from the list. If only one of the two
        // cells that the wall divides is visited, then:
        // __ __ __ 1. Make the wall a passage and mark the unvisited cell as
        // part of the maze.
        // __ __ __ 2. Add the neighboring walls of the cell to the wall list.
        // __ __ c. Remove the wall from the list.

        // pick a cell
        final int i = 0;
        final int j = 0;

        // mark is part of the maze (visited)
        cells[i][j] = true;

        // add the walls to the cell wall list
        addCellWallsToList(i, j);

        // while there are walls in the list
        while (!wallList.isEmpty()) {

            // pick a random wall from the list
            int randomWallIndex = rand.nextInt(wallList.size());
            final Wall wall = wallList.get(randomWallIndex);

            if (wall.wallType == WallType.HORIZONTAL) {

                final boolean cell1 = cells[wall.i][wall.j];
                final boolean cell2 = cells[wall.i][wall.j - 1];

                // if only one of the two cells that the wall divides is visited
                // then
                if (!cell1) {

                    // make the wall a passage and mark the unvisited cell as
                    // part of the maze
                    wallsH[wall.i][wall.j] = true;
                    cells[wall.i][wall.j] = true;
                    cells[wall.i][wall.j - 1] = true;

                    // add neighboring walls of the cell to the wall list
                    addCellWallsToList(wall.i, wall.j);

                } else if (!cell2) {

                    // make the wall a passage and mark the unvisited cell as
                    // part of the maze
                    wallsH[wall.i][wall.j] = true;
                    cells[wall.i][wall.j] = true;
                    cells[wall.i][wall.j - 1] = true;

                    // add neighboring walls of the cell to the wall list
                    addCellWallsToList(wall.i, wall.j - 1);

                }

                // remove the wall from the list
                wallList.remove(randomWallIndex);

            } else if (wall.wallType == WallType.VERTICAL) {

                final boolean cell1 = cells[wall.i][wall.j];
                final boolean cell2 = cells[wall.i - 1][wall.j];

                // if only one of the two cells that the wall divides is visited
                // then
                if (!cell1) {

                    // make the wall a passage and mark the unvisited cell as
                    // part of the maze
                    wallsV[wall.i][wall.j] = true;
                    cells[wall.i][wall.j] = true;
                    cells[wall.i - 1][wall.j] = true;

                    // add neighboring walls of the cell to the wall list
                    addCellWallsToList(wall.i, wall.j);

                } else if (!cell2) {

                    // make the wall a passage and mark the unvisited cell as
                    // part of the maze
                    wallsV[wall.i][wall.j] = true;
                    cells[wall.i][wall.j] = true;
                    cells[wall.i - 1][wall.j] = true;

                    // add neighboring walls of the cell to the wall list
                    addCellWallsToList(wall.i - 1, wall.j);

                }

                // remove the wall from the list
                wallList.remove(randomWallIndex);

            } else {
                throw new IllegalStateException();
            }
        }
    }

    public String toString() {
        final char tickChar = 'X';

        final StringBuilder sb = new StringBuilder();

        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                if (wallsH[i][j]) {
                    sb.append(tickChar);
                    sb.append(" ");
                } else {
                    sb.append(tickChar);
                    sb.append("X");
                }
            }
            sb.append(tickChar);
            sb.append("\n");

            for (int i = 0; i < width; ++i) {
                if (wallsV[i][j]) {
                    sb.append(" ");
                } else {
                    sb.append("X");
                }
                if (cells[i][j]) {
                    sb.append(" ");
                } else {
                    sb.append(" ");
                }
            }
            if (wallsV[width][j]) {
                sb.append(" ");
            } else {
                sb.append("X");
            }
            sb.append("\n");
        }

        for (int i = 0; i < width; ++i) {
            if (wallsH[i][height]) {
                sb.append(tickChar);
                sb.append(" ");
            } else {
                sb.append(tickChar);
                sb.append("X");
            }
        }
        sb.append(tickChar);
        sb.append("\n");

        return sb.toString();
    }

    public String toString2() {
        final char tickChar = '+';

        final StringBuilder sb = new StringBuilder();

        sb.append("   ");
        for (int i = 0; i < height; ++i) {
            sb.append(String.format(" %03d", i));
        }
        sb.append('\n');

        for (int j = 0; j < height; ++j) {
            sb.append("   ");
            for (int i = 0; i < width; ++i) {
                if (wallsH[i][j]) {
                    sb.append(tickChar);
                    sb.append("   ");
                } else {
                    sb.append(tickChar);
                    sb.append("---");
                }
            }
            sb.append(tickChar);
            sb.append("\n");

            sb.append(String.format("%03d", j));
            for (int i = 0; i < width; ++i) {
                if (wallsV[i][j]) {
                    sb.append(" ");
                } else {
                    sb.append("|");
                }
                if (cells[i][j]) {
                    sb.append("   ");
                } else {
                    sb.append(" * ");
                }
            }
            if (wallsV[width][j]) {
                sb.append(" ");
            } else {
                sb.append("|");
            }
            sb.append(String.format("%03d\n", j));
        }

        sb.append("   ");
        for (int i = 0; i < width; ++i) {
            if (wallsH[i][height]) {
                sb.append(tickChar);
                sb.append("   ");
            } else {
                sb.append(tickChar);
                sb.append("---");
            }
        }
        sb.append(tickChar);
        sb.append("\n");

        sb.append("   ");
        for (int i = 0; i < height; ++i) {
            sb.append(String.format(" %03d", i));
        }
        sb.append('\n');

        return sb.toString();
    }
}
