package maze.solver;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graph.Search;
import graph.SearchImplBreadthFirst;
import graph.model.Vertex;
import maze.sample.AbstractMazeSample;
import maze.sample.MazeSampleFromPdf;
import maze.util.FileUtil;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws IOException {
        final Main main = new Main();
        main.go();
    }

    private void go() throws IOException {

        final AbstractMazeSample mazeSample = new MazeSampleFromPdf();

        LOG.info("reading maze sample text file: {}", mazeSample.getFileName());
        final String text = FileUtil.readText(mazeSample.getFileName());
        LOG.info("maze sample text: [\n{}]", text);

        LOG.trace("parsing text to create graph");
        final String[] textLines = StringUtils.split(text, "\r\n");
        if (textLines.length < 1) {
            throw new IllegalStateException();
        }
        final MazeGraph mazeGraph = new MazeGraph();
        mazeGraph.buildGraph(textLines);

        LOG.trace("getting start vertex");
        final Vertex startVertex = mazeGraph.getVertexByLocation(mazeSample.getStartI(), mazeSample.getStartJ());
        if (startVertex == null) {
            LOG.error("start vertex not found: {},{}", mazeSample.getStartI(), mazeSample.getStartJ());
            return;
        }
        LOG.info("start vertex: {}", startVertex.getId());

        LOG.trace("getting end vertex");
        final Vertex endVertex = mazeGraph.getVertexByLocation(mazeSample.getEndI(), mazeSample.getEndJ());
        if (endVertex == null) {
            LOG.error("end vertex not found: {},{}", mazeSample.getEndI(), mazeSample.getEndJ());
            return;
        }
        LOG.info("end vertex: {}", endVertex.getId());

        LOG.trace("searching graph for path");
        final Search graphSearch = new SearchImplBreadthFirst();
        final Vertex resultVertex = graphSearch.search(endVertex, startVertex);
        if (resultVertex == null) {
            LOG.info("path not found");
            LOG.trace("exiting");
            return;
        }

        final MazePath mazePath = new MazePath(resultVertex);
        LOG.info("path: {}", mazePath);

        LOG.info("maze text with marked path: [\n{}]", mazePath.createMazeTextWithMarkedPath(textLines));
    }
}
