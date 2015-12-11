package org.apache.camel.component.neo4j;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.neo4j.dto.Query;
import org.apache.camel.component.neo4j.dto.Traverse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.DelegatingGraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Stephen K Samuel samspade79@gmail.com
 * 
 */
public class Neo4jProducerTest {

	private EmbeddedGraphDatabase	db;

	@Mock
	private Neo4jEndpoint		endpoint;

	@Mock
	private Exchange			exchange;

	private Neo4jProducer		producer;

	@Mock
	private Message			msg;

	@Mock
	private Neo4jTemplate		template;

	@Before
	public void before() throws IOException {
		initMocks(this);
		when(exchange.getIn()).thenReturn(msg);
		db = (EmbeddedGraphDatabase) new GraphDatabaseFactory().newEmbeddedDatabase(getRandomStore());
		producer = new Neo4jProducer(endpoint, new DelegatingGraphDatabase(db), template);
	}

	String getRandomStore() throws IOException {
		File file = File.createTempFile("prefix", "suffix").getParentFile();
		File dir = new File(file.getAbsolutePath() + "/neo4j-test-" + UUID.randomUUID());
		dir.mkdir();
		return dir.getAbsolutePath();
	}

	@Test
	public void testCreateNodeEmptyBody() throws Exception {
		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.CREATE_NODE);
		Node node = mock(Node.class);
		when(node.getId()).thenReturn(14L);
		when(template.createNode()).thenReturn(node);
		producer.process(exchange);
		verify(template).createNode();
		verify(msg).setHeader(Neo4jEndpoint.HEADER_NODE_ID, 14L);
	}

	@Test
	public void testCreateNodePropertiesBody() throws Exception {
		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.CREATE_NODE);
		when(msg.getBody()).thenReturn(new HashMap<String, Object>());
		Node node = mock(Node.class);
		when(node.getId()).thenReturn(14L);
		when(template.createNode(anyMap())).thenReturn(node);
		producer.process(exchange);
		verify(template).createNode(anyMap());
		verify(msg).setHeader(Neo4jEndpoint.HEADER_NODE_ID, 14L);
	}

	@Test
	public void testCreateRelationshipWithBasicBody() throws Exception {

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.CREATE_RELATIONSHIP);

		Node start = mock(Node.class);
		Node end = mock(Node.class);
		String type = "friendswith";

		BasicRelationship br = new BasicRelationship(start, end, type);
		when(msg.getBody()).thenReturn(br);

		Relationship r = mock(Relationship.class);
		when(r.getId()).thenReturn(99L);

		when(template.createRelationshipBetween(start, end, type, null)).thenReturn(r);

		producer.process(exchange);
		verify(template).createRelationshipBetween(start, end, type, null);

		verify(msg).setHeader(Neo4jEndpoint.HEADER_RELATIONSHIP_ID, 99L);
	}

	@Test
	public void testCreateRelationshipWithSpringBody() throws Exception {

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.CREATE_RELATIONSHIP);

		Object start = new Object();
		Object end = new Object();
		Class entityClass = String.class;
		String type = "friendswith";

		SpringDataRelationship spring = new SpringDataRelationship(start, end, entityClass, type, true);
		when(msg.getBody()).thenReturn(spring);

		Relationship r = mock(Relationship.class);
		when(r.getId()).thenReturn(55L);

		when(template.createRelationshipBetween(start, end, entityClass, type, true)).thenReturn(r);

		producer.process(exchange);
		verify(template).createRelationshipBetween(start, end, entityClass, type, true);

		verify(msg).setHeader(Neo4jEndpoint.HEADER_RELATIONSHIP_ID, 55L);
	}

	@Test(expected = Neo4jException.class)
	public void testNullOperationFails() throws Exception {
		producer.process(exchange);
	}

	@Test
	public void testRemoveNodeBasicBody() throws Exception {

		Node node = mock(Node.class);
		when(node.getId()).thenReturn(14L);

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.REMOVE_NODE);
		when(msg.getBody()).thenReturn(node);

		producer.process(exchange);
		verify(template).delete(node);
	}

	@Test
	public void testRemoveNodeById() throws Exception {

		Node node = mock(Node.class);
		when(template.getNode(44L)).thenReturn(node);

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.REMOVE_NODE);
		when(msg.getBody()).thenReturn(44L);

		producer.process(exchange);
		verify(template).delete(node);
	}

	@Test
	public void testRemoveRelationshipByBasic() throws Exception {

		Relationship r = mock(Relationship.class);

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.REMOVE_RELATIONSHIP);
		when(msg.getBody()).thenReturn(r);

		producer.process(exchange);
		verify(template).delete(r);
	}

	@Test
	public void testRemoveRelationshipById() throws Exception {

		Relationship r = mock(Relationship.class);
		when(template.getRelationship(51L)).thenReturn(r);

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.REMOVE_RELATIONSHIP);
		when(msg.getBody()).thenReturn(51L);

		producer.process(exchange);
		verify(template).delete(r);
	}

	@Test
	public void testRemoveRelationshipBySpringData() throws Exception {

		Object start = new Object();
		Object end = new Object();
		Class entityClass = String.class;
		String type = "friendswith";

		SpringDataRelationship spring = new SpringDataRelationship(start, end, entityClass, type, true);

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.REMOVE_RELATIONSHIP);
		when(msg.getBody()).thenReturn(spring);

		producer.process(exchange);
		verify(template).deleteRelationshipBetween(start, end, type);
	}

	@Test(expected = Neo4jException.class)
	public void testUnsupportedBodyForCreateNode() throws Exception {

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.CREATE_NODE);
		when(msg.getBody()).thenReturn(new Object());
		producer.process(exchange);
	}

	@Test(expected = Neo4jException.class)
	public void testUnsupportedBodyForCreateRelationship() throws Exception {

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.CREATE_RELATIONSHIP);
		when(msg.getBody()).thenReturn(new Object());
		producer.process(exchange);
	}

	@Test(expected = Neo4jException.class)
	public void testUnsupportedBodyForDeleteNode() throws Exception {

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.REMOVE_NODE);
		when(msg.getBody()).thenReturn(new Object());
		producer.process(exchange);
	}

	@Test(expected = Neo4jException.class)
	public void testUnsupportedBodyForDeleteRelationship() throws Exception {

		when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.REMOVE_RELATIONSHIP);
		when(msg.getBody()).thenReturn(new Object());
		producer.process(exchange);
	}

    @Test
    public void testTraverse() throws Exception {

        when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.TRAVERSE);
        Node node = mock(Node.class);
        when(node.getId()).thenReturn(55L);
        TraversalDescription traversalDescription = mock(TraversalDescription.class);
        when(msg.getBody()).thenReturn(new Traverse(node, traversalDescription));
        Result<Path> result = mock(Result.class);
        when(template.traverse(node, traversalDescription)).thenReturn(result);
        producer.process(exchange);
        verify(template).traverse(node, traversalDescription);
        verify(msg).setHeader(Neo4jEndpoint.HEADER_TRAVERSE_RESULT, result);
    }

    @Test(expected = Neo4jException.class)
    public void testTraverseUnsupportedBody() throws Exception {

        when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.TRAVERSE);
        when(msg.getBody()).thenReturn(new Object());
        producer.process(exchange);
    }

    @Test(expected = Neo4jException.class)
    public void testTraverseNullBody() throws Exception {

        when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.TRAVERSE);
        when(msg.getBody()).thenReturn(null);
        producer.process(exchange);
    }

    @Test
    public void testQuery() throws Exception {

        when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.QUERY);
        String statement = "";
        Map<String, Object> map = mock(HashMap.class);
        when(msg.getBody()).thenReturn(new Query(statement, map));
        Result<Map<String, Object>> result = mock(Result.class);
        when(template.query(anyString(), anyMap())).thenReturn(result);
        producer.process(exchange);
        verify(template).query(anyString(), anyMap());
        verify(msg).setHeader(Neo4jEndpoint.HEADER_QUERY_RESULT, result);
    }

    @Test(expected = Neo4jException.class)
    public void testQueryUnsupportedBody() throws Exception {

        when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.QUERY);
        when(msg.getBody()).thenReturn(new Object());
        producer.process(exchange);
    }

    @Test(expected = Neo4jException.class)
    public void testQueryNullBody() throws Exception {

        when(msg.getHeader(Neo4jEndpoint.HEADER_OPERATION)).thenReturn(Neo4jOperation.QUERY);
        when(msg.getBody()).thenReturn(null);
        producer.process(exchange);
    }
}
