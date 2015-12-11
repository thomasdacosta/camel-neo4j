camel-neo4j
========

*This component is now included in the [camel extra](http://camel.apache.org/spring-neo4j.html) distribution from version 2.10+*


The camel-neo4j component allows you to treat Neo4j as a camel producer endpoint. This means you can use this component in to() calls but not from() calls. This component is backed by the Spring Data Neo4j Library.

* As a producer, can create or remove nodes, and create or remove relationships.
* Can support as many endpoints as required, eg for multiple databases across multiple instances.
* Headers set for node id (for created nodes), relationship id (for created relationships)

How to Use
==========

You can use neo4j as an endpoint with the following URI.
`neo4j:http://hostname[:port]/database[?options]`

The available options are

* user - username to Neo4j
* password - password to Neo4j

Then you need to set a header on each message, one of the following enum types

    `CREATE_NODE,

	REMOVE_NODE,

	CREATE_RELATIONSHIP,

	REMOVE_RELATIONSHIP,

	TRAVERSE,

	QUERY`

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

For TRAVERSE:

* null body - throws Neo4jException
* camel-neo4j Traverse dto instance - traverse node using TraversalDescription

From QUERY:

* null body - throws Neo4jException
* camel-neo4j Query dto instance - query using String statement and parameters Map

Tests
=====

camel-neo4j has a complete set of unit tests. In addition there are some integration tests that require you to run a local instance of neo4j. By default the tests will look at localhost:7474 on http://localhost:7474/db/data/

## License
```
This software is licensed under the Apache 2 license, quoted below.

Copyright 2013 Stephen Samuel

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```
