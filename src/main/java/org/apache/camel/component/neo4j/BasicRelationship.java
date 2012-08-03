package org.apache.camel.component.neo4j;

import java.util.Map;

import org.neo4j.graphdb.Node;

/**
 * @author Stephen K Samuel samspade79@gmail.com 3 Aug 2012 00:13:53
 * 
 */
public class BasicRelationship {

	private final Node			start;
	private final Node			end;
	private final String			relationshipType;
	private final Map<String, Object>	properties;

	public BasicRelationship(Node start, Node end, String relationshipType) {
		this(start, end, relationshipType, null);
	}

	public BasicRelationship(Node start, Node end, String relationshipType, Map<String, Object> properties) {
		this.start = start;
		this.end = end;
		this.relationshipType = relationshipType;
		this.properties = properties;
	}

	public Node getEnd() {
		return end;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public Node getStart() {
		return start;
	}
}
