package org.apache.camel.component.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

public class BasicNeo4jCreateRelationshipMessage implements Neo4jCreateRelationshipMessage {

	private final Map<String, Object>	props;
	private final RelationshipType	relationshipType;
	private final Node			endNode;
	private final Node			startNode;

	public BasicNeo4jCreateRelationshipMessage(Node startNode, Node endNode, RelationshipType relationshipType) {
		this(startNode, endNode, relationshipType, new HashMap<String, Object>());
	}

	public BasicNeo4jCreateRelationshipMessage(Node startNode, Node endNode, RelationshipType relationshipType, Map<String, Object> props) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.relationshipType = relationshipType;
		this.props = props;
	}

	@Override
	public Node getEndNode() {
		return endNode;
	}

	@Override
	public Map<String, Object> getProperties() {
		return props;
	}

	@Override
	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	@Override
	public Node getStartNode() {
		return startNode;
	}

}
