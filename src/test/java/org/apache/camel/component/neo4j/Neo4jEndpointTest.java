package org.apache.camel.component.neo4j;

import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.Test;

/**
 * @author Stephen K Samuel samspade79@gmail.com 29 Jul 2012 16:45:20
 * 
 */
public class Neo4jEndpointTest {

	@Test
	public void assertSingleton() throws URISyntaxException {
		Neo4jEndpoint endpoint = new Neo4jEndpoint("neo4j:http://localhost/db", "http://localhost/db", new Neo4jComponent(), new HashMap<String, Object>());
		assertTrue(endpoint.isSingleton());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testConsumerUnsupported() throws Exception {
		new Neo4jEndpoint("ignored", "http://localhost:80/db", new Neo4jComponent(), new HashMap<String, Object>()).createConsumer(null);
	}
}
