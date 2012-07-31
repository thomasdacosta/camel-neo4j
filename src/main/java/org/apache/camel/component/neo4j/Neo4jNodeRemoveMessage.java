package org.apache.camel.component.neo4j;

import org.neo4j.graphdb.Node;

/**
 * @author Stephen K Samuel samspade79@gmail.com 31 Jul 2012 11:24:39
 * 
 */
public interface Neo4jNodeRemoveMessage {

	Node getNode();

}
