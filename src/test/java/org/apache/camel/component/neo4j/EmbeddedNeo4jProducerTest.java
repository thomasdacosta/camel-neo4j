package org.apache.camel.component.neo4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * @author Stephen K Samuel samspade79@gmail.com 2 Aug 2012 11:53:28
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class EmbeddedNeo4jProducerTest {

	enum Relationships implements RelationshipType {
		MEMBER_OF
	}

	@Mock
	private Neo4jEndpoint		endpoint;

	private EmbeddedNeo4jProducer	producer;

	private EmbeddedGraphDatabase	g;

	@After
	public void after() {
		g.shutdown();
	}

	@Before
	public void before() throws IOException {
		initMocks(this);
		g = new EmbeddedGraphDatabase(getTempDir());
		producer = new EmbeddedNeo4jProducer(endpoint, g);
	}

	String getTempDir() throws IOException {
		String parent = File.createTempFile("prefix", "suffix").getParent();
		File dir = new File(parent + "/camel-neo4j-test-" + RandomStringUtils.randomAlphanumeric(10));
		return dir.getAbsolutePath();
	}

	@Test
	public void testCreateEmptyNode() throws IOException {
		Node node = producer.handle(new BasicNeo4jCreateNodeMessage());
		assertNotNull(node);
		assertNotNull(producer.getGraphDatabase().getNodeById(node.getId()));
	}

	@Test
	public void testCreateNodeWithProperties() throws IOException {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("name", "chrismartin");
		props.put("nationality", "English");
		Node node = producer.handle(new BasicNeo4jCreateNodeMessage(props));
		assertNotNull(node);
		assertEquals("chrismartin", node.getProperty("name"));
		assertEquals("English", node.getProperty("nationality"));
	}

	@Test
	public void testCreateRelationship() throws IOException {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("name", "chrismartin");
		Node start = producer.handle(new BasicNeo4jCreateNodeMessage(props));

		props = new HashMap<String, Object>();
		props.put("band", "coldplay");
		Node end = producer.handle(new BasicNeo4jCreateNodeMessage(props));

		Relationship r = producer.handle(new BasicNeo4jCreateRelationshipMessage(start, end, Relationships.MEMBER_OF));
		assertNotNull(r);
		assertNotNull(producer.getGraphDatabase().getRelationshipById(r.getId()));
	}
}
