package org.apache.camel.component.neo4j;

import java.util.Map;

public class BasicNeo4jNodeCreateMessage implements Neo4jNodeCreateMessage {

	private final Map<String, Object>	props;

	public BasicNeo4jNodeCreateMessage(Map<String, Object> props) {
		this.props = props;
	}

	@Override
	public Map<String, Object> getProps() {
		return props;
	}

}
