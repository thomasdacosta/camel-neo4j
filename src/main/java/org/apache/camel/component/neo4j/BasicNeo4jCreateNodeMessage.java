package org.apache.camel.component.neo4j;

import java.util.HashMap;
import java.util.Map;

public class BasicNeo4jCreateNodeMessage implements Neo4jCreateNodeMessage {

	private final Map<String, Object>	props;

	public BasicNeo4jCreateNodeMessage() {
		this.props = new HashMap<String, Object>();
	}

	public BasicNeo4jCreateNodeMessage(Map<String, Object> props) {
		this.props = props;
	}

	@Override
	public Map<String, Object> getProps() {
		return props;
	}

}
