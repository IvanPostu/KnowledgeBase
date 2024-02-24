package com.ivan.knowledgebase.server.embedded.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ivan.knowledgebase.server.embedded.impl.HelloEndpointsImpl.Person;

@Path("/hello")
public interface HelloEndpoints {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{name}")
	String sayHello(@PathParam("name") String name);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Person getPerson();
}
