package graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import graph.model.Vertex;

//
// https://en.m.wikipedia.org/wiki/Breadth-first_search
// 
// Breadth-First-Search(Graph, root):
//
// __ create empty set S
// __ create empty queue Q
//
// __ root.parent = NIL
// __ add root to S
// __ Q.enqueue(root)
//
// __ while Q is not empty:
// __ __ current = Q.dequeue()
// __ __ if current is the goal:
// __ __ __ return current
// __ __ for each node n that is adjacent to current:
// __ __ __ if n is not in S:
// __ __ __ __ add n to S
// __ __ __ __ n.parent = current
// __ __ __ __ Q.enqueue(n)

public class SearchImplBreadthFirst implements Search {

    @Override
    public Vertex search(final Vertex startVertex, final Vertex endVertex) {
        final Vertex root = startVertex;

        final Set<Vertex> S = new HashSet<Vertex>();
        final Queue<Vertex> Q = new LinkedList<Vertex>();

        root.setParent(null);
        S.add(root);
        Q.add(root);

        while (!Q.isEmpty()) {
            final Vertex current = Q.remove();
            if (current == endVertex) {
                return endVertex;
            }
            final List<Vertex> adjcentToCurrent = current.getAdjacentVertices();
            for (final Vertex n : adjcentToCurrent) {
                if (!S.contains(n)) {
                    S.add(n);
                    n.setParent(current);
                    Q.add(n);
                }
            }
        }

        return null;
    }
}
