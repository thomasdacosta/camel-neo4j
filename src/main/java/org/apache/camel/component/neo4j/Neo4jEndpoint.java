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

import java.net.URISyntaxException;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

public class Neo4jEndpoint extends DefaultEndpoint {

	private boolean				commits			= true;
	private boolean				rollbacks			= true;

	public static final String		HEADER_TXTYPE		= "Neo4jTxType";
	public static final String		HEADER_NODE_ID		= "Neo4jNodeId";
	public static final String		HEADER_RELATIONSHIP_ID	= "Neo4jRelationshipId";

	private final GraphDatabaseService	graphDatabase;

	public Neo4jEndpoint(String endpointUri, String remaining, Neo4jComponent component) throws URISyntaxException {
		super(endpointUri, component);
		graphDatabase = new SpringRestGraphDatabase(remaining);
	}

	public Exchange createCommitExchange(TransactionData data, Object state) {
		Exchange exchange = new DefaultExchange(getCamelContext(), getExchangePattern());

		Message message = new DefaultMessage();
		message.setHeader(HEADER_TXTYPE, "COMMIT");
		message.setBody(data);
		exchange.setIn(message);
		return exchange;
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		return new Neo4jConsumer(this, graphDatabase, processor, commits, rollbacks);
	}

	@Override
	public Producer createProducer() throws Exception {
		if (graphDatabase instanceof SpringRestGraphDatabase)
			return new SpringRestNeo4jProducer(this, (SpringRestGraphDatabase) graphDatabase);
		else
			return new EmbeddedNeo4jProducer(this, (EmbeddedGraphDatabase) graphDatabase);
	}

	public Exchange createRollbackExchange(TransactionData data, Object state) {
		Exchange exchange = new DefaultExchange(getCamelContext(), getExchangePattern());

		Message message = new DefaultMessage();
		message.setHeader(HEADER_TXTYPE, "ROLLBACK");
		message.setBody(data);
		exchange.setIn(message);
		return exchange;
	}

	public boolean isCommits() {
		return commits;
	}

	public boolean isRollbacks() {
		return rollbacks;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setCommits(boolean commits) {
		this.commits = commits;
	}

	public void setRollbacks(boolean rollbacks) {
		this.rollbacks = rollbacks;
	}

}
