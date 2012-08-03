package org.apache.camel.component.neo4j;

/**
 * @author Stephen K Samuel samspade79@gmail.com 3 Aug 2012 00:06:35
 * 
 */
public class SpringDataRelationship {

	private final Object	start;
	private final Object	end;
	private final Class	relationshipEntityClass;
	private final String	relationshipType;
	private final boolean	allowDuplicates;

	public SpringDataRelationship(Object start, Object end, Class relationshipEntityClass, String relationshipType,
			boolean allowDuplicates) {
		this.start = start;
		this.end = end;
		this.relationshipEntityClass = relationshipEntityClass;
		this.relationshipType = relationshipType;
		this.allowDuplicates = allowDuplicates;
	}

	public Object getEnd() {
		return end;
	}

	public Class getRelationshipEntityClass() {
		return relationshipEntityClass;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public Object getStart() {
		return start;
	}

	public boolean isAllowDuplicates() {
		return allowDuplicates;
	}
}
