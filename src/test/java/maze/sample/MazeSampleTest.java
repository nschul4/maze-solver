package maze.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import graph.Search;
import graph.SearchImplBreadthFirst;
import graph.model.Vertex;
import maze.solver.MazeGraph;
import maze.solver.MazePath;
import maze.util.FileUtil;

public class MazeSampleTest {

    private static final Logger LOG = Logger.getLogger(MazeSampleTest.class);

    final MazeGraph mazeGraph = new MazeGraph();

    private void validateMazeSample(final AbstractMazeSample mazeSample) throws IOException {
        assertNotNull(mazeSample.getFileName());
        final String expectedFileName = "sample/" + mazeSample.getClass().getSimpleName() + ".txt";
        assertEquals(expectedFileName, mazeSample.getFileName());
        LOG.info("reading maze sample text file: " + mazeSample.getFileName());
        final String text = FileUtil.readText(mazeSample.getFileName());
        LOG.info("maze sample text: [\n" + text + "]");
        final String[] textLines = FileUtil.splitTextIntoTextLines(text);
        mazeGraph.buildGraph(textLines);
        final Vertex startVertex = mazeGraph.getVertexByLocation(mazeSample.getStartI(), mazeSample.getStartJ());
        final Vertex endVertex = mazeGraph.getVertexByLocation(mazeSample.getEndI(), mazeSample.getEndJ());
        if (startVertex == null || endVertex == null) {
            LOG.info("\n" +
            //
                    "getStartI=" + mazeSample.getStartI() + ", getStartJ=" + mazeSample.getStartJ() + "\n" +
                    //
                    "getEndI=" + mazeSample.getEndI() + ", getEndJ=" + mazeSample.getEndJ() + "\n" +
                    //
                    text
            //
            );
        }
        assertNotNull("getStartI=" + mazeSample.getStartI() + ", getStartJ=" + mazeSample.getStartJ(), startVertex);
        assertNotNull("getEndI=" + mazeSample.getEndI() + ", getEndJ=" + mazeSample.getEndJ(), endVertex);
        final Search graphSearch = new SearchImplBreadthFirst();
        final Vertex resultVertex = graphSearch.search(endVertex, startVertex);
        assertNotNull(resultVertex);
        final MazePath mazePath = new MazePath(resultVertex);
        LOG.info("path=" + mazePath);
        LOG.info("maze text with marked path: [\n" + mazePath.createMazeTextWithMarkedPath(textLines) + "]");
    }

    @Test
    public void testMazeSample2() throws IOException {
        validateMazeSample(new MazeSample2());
    }

    @Test
    public void testMazeSample3() throws IOException {
        validateMazeSample(new MazeSample3());
    }

    @Test
    public void testMazeSample4() throws IOException {
        validateMazeSample(new MazeSample4());
    }

    @Test
    public void testMazeSample5() throws IOException {
        validateMazeSample(new MazeSample5());
    }

    @Test
    public void testMazeSampleFromPdf() throws IOException {
        validateMazeSample(new MazeSampleFromPdf());
    }
}
