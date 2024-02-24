package com.ivan.knowledgebase.server.embedded.impl;

import javax.ws.rs.ext.Provider;

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

}
