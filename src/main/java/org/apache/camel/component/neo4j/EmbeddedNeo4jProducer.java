package org.apache.camel.component.neo4j;

import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
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

	public EmbeddedGraphDatabase getGraphDatabase() {
		return graphDatabase;
	}

	@Override
	protected Node handle(Neo4jCreateNodeMessage msg) {
		Transaction tx = graphDatabase.beginTx();
		try {
			Node node = graphDatabase.createNode();
			for (Map.Entry<String, Object> entry : msg.getProps().entrySet()) {
				node.setProperty(entry.getKey(), entry.getValue());
			}
			tx.success();
			return node;
		} finally {
			tx.finish();
		}
	}

	@Override
	protected Relationship handle(Neo4jCreateRelationshipMessage msg) {
		Transaction tx = graphDatabase.beginTx();
		try {
			Node start = graphDatabase.getNodeById(msg.getStartNode().getId());
			Relationship relationship = start.createRelationshipTo(msg.getEndNode(), msg.getRelationshipType());
			for (Map.Entry<String, Object> entry : msg.getProperties().entrySet()) {
				relationship.setProperty(entry.getKey(), entry.getValue());
			}
			tx.success();
			return relationship;
		} finally {
			tx.finish();
		}
	}

	@Override
	protected void handle(Neo4jRemoveNodeMessage msg) {
		Transaction tx = graphDatabase.beginTx();
		try {
			graphDatabase.getNodeById(msg.getNode().getId()).delete();
			tx.success();
		} finally {
			tx.finish();
		}
	}

	@Override
	protected void handle(Neo4jRemoveRelationshipMessage msg) {
		Transaction tx = graphDatabase.beginTx();
		try {
			graphDatabase.getRelationshipById(msg.getRelationship().getId()).delete();
			tx.success();
		} finally {
			tx.finish();
		}
	}
}
