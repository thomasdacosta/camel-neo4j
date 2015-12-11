/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.camel.component.neo4j;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.data.neo4j.rest.SpringCypherRestGraphDatabase;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Stephen K Samuel
 */
public class Neo4jEndpoint extends DefaultEndpoint {

    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static final String HEADER_OPERATION = "Neo4jOperation";
    public static final String HEADER_NODE_ID = "Neo4jNodeId";
    public static final String HEADER_RELATIONSHIP_ID = "Neo4jRelationshipId";
    public static final String HEADER_TRAVERSE_RESULT = "Neo4jTraverseResult";
    public static final String HEADER_QUERY_RESULT = "Neo4jQueryResult";

    private final GraphDatabaseService graphDatabase;

    public Neo4jEndpoint(String endpointUri, String remaining, Neo4jComponent component, Map<String, Object> params) {
        super(endpointUri.split("\\?")[0], component);

        if (!params.isEmpty()) {
            if (params.containsKey(USER) && params.containsKey(PASSWORD))
                graphDatabase = new SpringCypherRestGraphDatabase(remaining, params.get(USER).toString(), params.get(PASSWORD).toString());
            else
                throw new InvalidParameterException("The only valid parameters are: [user,password].");
        } else
            graphDatabase = new SpringCypherRestGraphDatabase(remaining);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new Neo4jProducer(this, (SpringCypherRestGraphDatabase) graphDatabase);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public boolean isLenientProperties() {
        return true;
    }
}
