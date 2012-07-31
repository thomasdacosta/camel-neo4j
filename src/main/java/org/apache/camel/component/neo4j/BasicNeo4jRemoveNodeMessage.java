package org.apache.camel.component.neo4j;

import org.neo4j.graphdb.Node;

public class BasicNeo4jRemoveNodeMessage implements Neo4jRemoveNodeMessage {

	private final Node	node;

	public BasicNeo4jRemoveNodeMessage(Node node) {
		this.node = node;
	}

	@Override
	public Node getNode() {
		return node;
	}

}
