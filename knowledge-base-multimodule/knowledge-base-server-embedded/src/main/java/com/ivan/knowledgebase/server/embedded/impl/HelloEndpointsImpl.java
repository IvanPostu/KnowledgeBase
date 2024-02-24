package com.ivan.knowledgebase.server.embedded.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivan.knowledgebase.server.embedded.api.HelloEndpoints;

@Provider
public class HelloEndpointsImpl implements HelloEndpoints {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

	@Override
	public Person getPerson() {
		return new Person("Ivan");
	}

	public static final class Person {
		private final String name;

		@JsonCreator
		public Person(@JsonProperty("name") String name) {
			this.name = name;
		}

		@JsonProperty("name")
		public String getName() {
			return name;
		}
	}

}
