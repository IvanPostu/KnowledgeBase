package com.ivan.knowledgebase.server.embedded;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(JacksonJsonProvider.class);
		packages("com.ivan.knowledgebase.server.rest.impl");

		setProperties(properties());
	}

	private static Map<String, String> properties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE.toString());
		properties.put("jersey.config.beanValidation.enableOutputValidationErrorEntity.server",
				Boolean.TRUE.toString());

		return Collections.unmodifiableMap(properties);
	}
}
