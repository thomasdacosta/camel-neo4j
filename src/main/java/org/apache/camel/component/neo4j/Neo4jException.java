/*
 * Copyright 2012 Stephen Keith Samuel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.camel.component.neo4j;

/**
 * @author Stephen K Samuel samspade79@gmail.com 29 Jul 2012 13:58:09
 * 
 */
public class Neo4jException extends RuntimeException {

	public Neo4jException(String message) {
		super(message);
	}

	public Neo4jException(Throwable cause) {
		super(cause);
	}

}
