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

import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.event.TransactionEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stephen K Samuel samspade79@gmail.com 21 May 2012 07:05:49
 * 
 */
public class Neo4jConsumer extends DefaultConsumer {

	private static final Logger			logger	= LoggerFactory.getLogger(Neo4jConsumer.class);
	private final GraphDatabaseService		graphDatabase;
	private final TransactionEventHandler	handler;

	public Neo4jConsumer(final Neo4jEndpoint endpoint, GraphDatabaseService graphDatabase, Processor processor, final boolean commits,
			final boolean rollbacks) {
		super(endpoint, processor);
		this.graphDatabase = graphDatabase;
		this.handler = new TransactionEventHandler() {

			@Override
			public Object beforeCommit(TransactionData data) throws Exception {
				return null;
			}

			@Override
			public void afterCommit(TransactionData data, Object state) {
				if (commits)
					endpoint.createCommitExchange(data, state);
			}

			@Override
			public void afterRollback(TransactionData data, Object state) {
				if (rollbacks)
					endpoint.createRollbackExchange(data, state);
			}
		};
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		graphDatabase.registerTransactionEventHandler(handler);
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
		graphDatabase.unregisterTransactionEventHandler(handler);
	}

	@Override
	public void resume() throws Exception {
		super.resume();
		doStart();
	}

	@Override
	public void suspend() throws Exception {
		super.suspend();
		doStop();
	}
}
