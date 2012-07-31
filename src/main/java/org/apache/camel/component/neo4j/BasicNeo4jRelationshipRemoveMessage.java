package org.apache.camel.component.neo4j;

import org.neo4j.graphdb.Relationship;

public class BasicNeo4jRelationshipRemoveMessage implements Neo4jRelationshipRemoveMessage {

	private final Relationship	relationship;

	public BasicNeo4jRelationshipRemoveMessage(Relationship relationship) {
		this.relationship = relationship;
	}

	@Override
	public Relationship getRelationship() {
		return relationship;
	}

}
