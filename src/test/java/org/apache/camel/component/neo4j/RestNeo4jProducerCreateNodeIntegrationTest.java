package org.apache.camel.component.neo4j;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

/**
 * @author Stephen K Samuel samspade79@gmail.com 29 Jul 2012 18:09:05
 * 
 */
public class RestNeo4jProducerCreateNodeIntegrationTest extends CamelTestSupport {

	private static final Logger			logger		= LoggerFactory
													.getLogger(RestNeo4jProducerCreateNodeIntegrationTest.class);

	private final String				neo4jEndpoint	= "neo4j:http://localhost:7474/db/data/";

	@SuppressWarnings("hiding")
	@Produce(uri = "direct:start")
	protected ProducerTemplate			template;

	private final SpringRestGraphDatabase	db			= new SpringRestGraphDatabase("http://localhost:7474/db/data/");

	@EndpointInject(uri = "mock:end")
	private MockEndpoint				end;

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:start").to(neo4jEndpoint).process(new Processor() {

					@Override
					public void process(Exchange arg0) throws Exception {
						Long id = (Long) arg0.getIn().getHeader(Neo4jEndpoint.HEADER_NODE_ID);
						assertNotNull(id);
						Node node = db.getNodeById(id);
						assertNotNull(node);
					}
				}).to(end);
			}
		};
	}

	@Test
	public void testCreateNodes() throws InterruptedException {

		final int messageCount = 100;
		end.expectedMessageCount(messageCount);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int k = 0; k < messageCount; k++) {
					template.sendBodyAndHeader(null, Neo4jEndpoint.HEADER_OPERATION, Neo4jOperation.CREATE_NODE);
				}
			}
		});
		t.start();
		t.join();
		end.assertIsSatisfied();
	}

}
