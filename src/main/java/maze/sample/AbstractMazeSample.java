package maze.sample;

public abstract class AbstractMazeSample {
    protected int startI = 0;
    protected int startJ = 0;
    protected int endI = 0;
    protected int endJ = 0;

    public String getFileName() {
        return "sample/" + this.getClass().getSimpleName() + ".txt";
    }

    public int getStartI() {
        return startI;
    }

    public int getStartJ() {
        return startJ;
    }

    public int getEndI() {
        return endI;
    }

    public int getEndJ() {
        return endJ;
    }
}
