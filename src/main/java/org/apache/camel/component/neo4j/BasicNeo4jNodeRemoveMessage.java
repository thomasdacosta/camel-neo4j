package org.apache.camel.component.neo4j;

import org.neo4j.graphdb.Node;

public class BasicNeo4jNodeRemoveMessage implements Neo4jNodeRemoveMessage {

	private final Node	node;

	public BasicNeo4jNodeRemoveMessage(Node node) {
		this.node = node;
	}

	@Override
	public Node getNode() {
		return node;
	}

}
