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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.core.GraphDatabase;

public class Neo4jProducer extends DefaultProducer {

	private static final Logger	logger	= LoggerFactory.getLogger(Neo4jProducer.class);

	private final Neo4jEndpoint	endpoint;
	private final GraphDatabase	graphDatabase;

	public Neo4jProducer(Neo4jEndpoint endpoint, GraphDatabase graphDatabase) {
		super(endpoint);
		this.graphDatabase = graphDatabase;
		this.endpoint = endpoint;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Object body = exchange.getIn().getBody();
		if (body instanceof Neo4jNodeCreateMessage) {

			Neo4jNodeCreateMessage create = (Neo4jNodeCreateMessage) body;
			graphDatabase.createNode(create.getProps());

		} else if (body instanceof Neo4jNodeRemoveMessage) {

			Neo4jNodeRemoveMessage remove = (Neo4jNodeRemoveMessage) body;
			graphDatabase.remove(remove.getNode());

		} else if (body instanceof Neo4jRelationshipRemoveMessage) {

			Neo4jRelationshipRemoveMessage remove = (Neo4jRelationshipRemoveMessage) body;
			graphDatabase.remove(remove.getRelationship());

		} else if (body instanceof Neo4jRelationshipCreateMessage) {

			Neo4jRelationshipCreateMessage create = (Neo4jRelationshipCreateMessage) body;
			graphDatabase.createRelationship(create.getStartNode(),
					create.getEndNode(),
					create.getRelationshipType(),
					create.getProperties());

		} else {
			throw new Neo4jException("Unsupported body type for Producer [" + body.getClass().getName() + "]");
		}
	}
}
