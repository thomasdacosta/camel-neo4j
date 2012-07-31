package org.apache.camel.component.neo4j;

import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * @author Stephen K Samuel samspade79@gmail.com 31 Jul 2012 12:05:26
 * 
 */
public class EmbeddedNeo4jProducer extends Neo4jProducer {

	private final EmbeddedGraphDatabase	graphDatabase;

	public EmbeddedNeo4jProducer(Neo4jEndpoint endpoint, EmbeddedGraphDatabase graphDatabase) {
		super(endpoint);
		this.graphDatabase = graphDatabase;
	}

	@Override
	protected Node handle(Neo4jCreateNodeMessage msg) {
		Neo4jCreateNodeMessage create = msg;
		Node node = graphDatabase.createNode();
		for (Map.Entry<String, Object> entry : create.getProps().entrySet()) {
			node.setProperty(entry.getKey(), entry.getValue());
		}
		return node;
	}

	@Override
	protected Relationship handle(Neo4jCreateRelationshipMessage msg) {
		Node start = graphDatabase.getNodeById(msg.getStartNode().getId());
		Relationship relationship = start.createRelationshipTo(msg.getEndNode(), msg.getRelationshipType());
		for (Map.Entry<String, Object> entry : msg.getProperties().entrySet()) {
			relationship.setProperty(entry.getKey(), entry.getValue());
		}
		return relationship;
	}

	@Override
	protected void handle(Neo4jRemoveRelationshipMessage msg) {
		graphDatabase.getRelationshipById(msg.getRelationship().getId()).delete();
	}

	@Override
	protected void handle(Neo4jRemoveNodeMessage msg) {
		graphDatabase.getNodeById(msg.getNode().getId()).delete();
	}
}
