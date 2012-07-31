package org.apache.camel.component.neo4j;

import org.neo4j.graphdb.Relationship;

public class BasicNeo4jRemoveRelationshipMessage implements Neo4jRemoveRelationshipMessage {

	private final Relationship	relationship;

	public BasicNeo4jRemoveRelationshipMessage(Relationship relationship) {
		this.relationship = relationship;
	}

	@Override
	public Relationship getRelationship() {
		return relationship;
	}

}
