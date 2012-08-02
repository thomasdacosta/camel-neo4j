package org.apache.camel.component.neo4j;

import java.util.Map;

import org.neo4j.graphdb.Node;

/**
 * @author Stephen K Samuel samspade79@gmail.com 3 Aug 2012 00:13:53
 * 
 */
public class BasicRelationship {

	private Node			start;
	private Node			end;
	private String			relationshipType;
	private Map<String, Object>	properties;

	public BasicRelationship() {
	}

	public BasicRelationship(Node start, Node end, String relationshipType) {
		this.start = start;
		this.end = end;
		this.relationshipType = relationshipType;
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

	public void setEnd(Node end) {
		this.end = end;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public void setStart(Node start) {
		this.start = start;
	}
}
