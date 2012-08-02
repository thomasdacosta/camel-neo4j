package org.apache.camel.component.neo4j;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

/**
 * @author Stephen K Samuel samspade79@gmail.com
 * 
 */
public class Neo4jProducerTest {

	private SpringRestGraphDatabase	db;

	private Neo4jEndpoint			endpoint;

	private Neo4jProducer			producer;

	@Before
	public void before() {
		producer = new Neo4jProducer(endpoint, db);
	}

	@Test
	public void testNullOperationFails() {

	}
}
