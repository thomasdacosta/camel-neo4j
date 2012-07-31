package org.apache.camel.component.neo4j;

import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

/**
 * @author Stephen K Samuel samspade79@gmail.com 31 Jul 2012 11:27:43
 * 
 */
public interface Neo4jRelationshipCreateMessage {

	Node getStartNode();

	Node getEndNode();

	RelationshipType getRelationshipType();

	Map<String, Object> getProperties();

}
