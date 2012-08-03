package org.apache.camel.component.neo4j;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Stephen K Samuel samspade79@gmail.com 29 Jul 2012 17:06:16
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class Neo4jComponentTest {

	@Mock
	private CamelContext	context;

	@Test
	public void testEndpointCreated() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		String uri = "neo4j:http://localhost:5984/db";
		String remaining = "http://localhost:5984/db";

		Endpoint endpoint = new Neo4jComponent(context).createEndpoint(uri, remaining, params);
		assertNotNull(endpoint);
	}
}
