package org.apache.camel.component.neo4j.dto;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.TraversalDescription;

/**
 * @author Renato Ochando
 *
 */
public class Traverse {
    private Node node;

    private TraversalDescription traversalDescription;

    public Traverse(Node node, TraversalDescription traversalDescription) {
        this.node = node;
        this.traversalDescription = traversalDescription;
    }

    public Node getNode() {
        return node;
    }

    public TraversalDescription getTraversalDescription() {
        return traversalDescription;
    }

}
