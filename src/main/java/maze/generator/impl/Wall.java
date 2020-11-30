package maze.generator.impl;

class Wall {
    public WallType wallType;
    public int i, j;

    public Wall(final WallType wallType, final int i, final int j) {
        this.i = i;
        this.j = j;
        this.wallType = wallType;
    }

    public Wall(final int i, final int j) {
        this.i = i;
        this.j = j;
    }
}
