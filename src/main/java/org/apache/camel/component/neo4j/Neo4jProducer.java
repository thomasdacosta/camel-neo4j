/*
 * Copyright 2012 Stephen Keith Samuel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.apache.camel.component.neo4j;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Neo4jProducer extends DefaultProducer {

	private static final Logger	logger	= LoggerFactory.getLogger(Neo4jProducer.class);

	private final Neo4jEndpoint	endpoint;

	public Neo4jProducer(Neo4jEndpoint endpoint) {
		super(endpoint);
		this.endpoint = endpoint;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Object body = exchange.getIn().getBody();
		if (body instanceof Neo4jCreateNodeMessage) {
			Node node = handle((Neo4jCreateNodeMessage) body);

		} else if (body instanceof Neo4jRemoveNodeMessage) {
			handle((Neo4jRemoveNodeMessage) body);

		} else if (body instanceof Neo4jRemoveRelationshipMessage) {
			handle((Neo4jRemoveRelationshipMessage) body);

		} else if (body instanceof Neo4jCreateRelationshipMessage) {
			handle((Neo4jCreateRelationshipMessage) body);

		} else {
			throw new Neo4jException("Unsupported body type for Producer [" + body.getClass().getName() + "]");
		}
	}

	protected abstract Relationship handle(Neo4jCreateRelationshipMessage msg);

	protected abstract void handle(Neo4jRemoveRelationshipMessage msg);

	protected abstract void handle(Neo4jRemoveNodeMessage msg);

	protected abstract Node handle(Neo4jCreateNodeMessage msg);
}
