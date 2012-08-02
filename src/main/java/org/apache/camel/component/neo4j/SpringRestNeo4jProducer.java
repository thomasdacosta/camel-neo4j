package org.apache.camel.component.neo4j;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

/**
 * @author Stephen K Samuel samspade79@gmail.com 31 Jul 2012 12:04:25
 * 
 */
public class SpringRestNeo4jProducer extends Neo4jProducer {

	private final SpringRestGraphDatabase	graphDatabase;

	public SpringRestNeo4jProducer(Neo4jEndpoint endpoint, SpringRestGraphDatabase graphDatabase) {
		super(endpoint);
		this.graphDatabase = graphDatabase;
	}

	@Override
	protected Relationship handle(Neo4jCreateRelationshipMessage msg) {
		return graphDatabase.createRelationship(msg.getStartNode(), msg.getEndNode(), msg.getRelationshipType(), msg.getProperties());
	}

	@Override
	protected void handle(Neo4jRemoveRelationshipMessage msg) {
		graphDatabase.remove(msg.getRelationship());
	}

	@Override
	protected void handle(Neo4jRemoveNodeMessage msg) {
		graphDatabase.remove(msg.getNode());
	}

	@Override
	protected Node handle(Neo4jCreateNodeMessage msg) {
		return graphDatabase.createNode(msg.getProps());
	}

}
