package org.apache.camel.component.neo4j.dto;

import java.util.Map;

/**
 * @author Renato Ochando
 *
 */
public class Query {
    private String statement;

    private Map<String, Object> params;

    public Query(String statement, Map<String, Object> params) {
        this.statement = statement;
        this.params = params;
    }

    public String getStatement() {
        return statement;
    }

    public Map<String, Object> getParams() {
        return params;
    }

}
