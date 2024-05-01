package com.ivan.knowledgebase.user.acceptance.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public enum BlackboxJerseyClient {
    INSTANCE;

    private static final int CONNECTION_MAX_PER_ROUTE = 100;
    private static final int CONNECTION_MAX_TOTAL = 500;

    BlackboxJerseyClient() {
        this.client = createClient();
    }

    private final Client client;

    public Client getClient() {
        return client;
    }

    private static Client createClient() {
        ClientConfig config = new ClientConfig();
        config.property(ApacheClientProperties.CONNECTION_MANAGER, createConnectionManager());
        config.connectorProvider(new ApacheConnectorProvider());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.CREATOR, Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.GETTER, Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.IS_GETTER, Visibility.ANY);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return ClientBuilder.newClient(config)
            .register(new JacksonJaxbJsonProvider(objectMapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));
    }

    private static HttpClientConnectionManager createConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(CONNECTION_MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(CONNECTION_MAX_PER_ROUTE);

        return connectionManager;
    }

}
