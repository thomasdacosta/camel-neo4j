camel-neo4j
========

The camel-neo4j component allows you to treat Neo4j as a camel producer endpoint. This means you can use this component in to() calls but not from() calls. This component is backed by the Spring Data Neo4j Library.

* As a producer, can create or remove nodes, and create or remove relationships.
* Can support as many endpoints as required, eg for multiple databases across multiple instances.
* Headers set for node id (for created nodes), relationship id (for created relationships)

How to Use
==========

You can use neo4j as an endpoint with the following URI.
`neo4j:http://hostname[:port]/database`

Then you need to set a header on each message, one of the following enum types

`	CREATE_NODE,

	REMOVE_NODE,

	CREATE_RELATIONSHIP,

	REMOVE_RELATIONSHIP`

Eg,

`exchange.getIn().setHeader(Neo4jEndpoint.HEADER_OPERATION, Neo4jOperation.CREATE_NODE);`

The body of the message is used to determine the node or relationship to manipulate. The following body types are supported:

For CREATE_NODE:

* null body - create default node
* Map body - create node with the properties set from the map

For REMOVE_NODE:

* Long or Integer - remove node using the body as the id
* neo4j Node instance - remove the node specified by that instance

 For CREATE_RELATIONSHIP:

* SpringDataRelationship - create relationship specified by any @NodeEntity annoted Spring entities.
* BasicRelationship - create relationship specified by the neo4j node types

For REMOVE_RELATIONSHIP:

* Long or Integer - remove relationship using the body as the id
* SpringDataRelationship - remove relationship specified by the @NodeEntity annoted Spring entities.
* BasicRelationship - remove relationship specified by the neo4j node types

Tests
=====

camel-neo4j has a complete set of unit tests. In addition there are some integration tests that require you to run a local instance of neo4j. By default the tests will look at localhost:7474 on http://localhost:7474/db/data/

- Initial contribution by Stephen Samuel.