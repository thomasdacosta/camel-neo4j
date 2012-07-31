package org.apache.camel.component.neo4j;

import org.neo4j.graphdb.Relationship;

public interface Neo4jRemoveRelationshipMessage {

	Relationship getRelationship();

}
