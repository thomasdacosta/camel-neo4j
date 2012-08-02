package org.apache.camel.component.neo4j;

/**
 * @author Stephen K Samuel samspade79@gmail.com 3 Aug 2012 00:06:35
 * 
 */
public class SpringDataRelationship {

	private Object	start;
	private Object	end;
	private Class	relationshipEntityClass;
	private String	relationshipType;
	private boolean	allowDuplicates;

	public SpringDataRelationship() {
	}

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

	public void setAllowDuplicates(boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
	}

	public void setEnd(Object end) {
		this.end = end;
	}

	public void setRelationshipEntityClass(Class relationshipEntityClass) {
		this.relationshipEntityClass = relationshipEntityClass;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public void setStart(Object start) {
		this.start = start;
	}
}
