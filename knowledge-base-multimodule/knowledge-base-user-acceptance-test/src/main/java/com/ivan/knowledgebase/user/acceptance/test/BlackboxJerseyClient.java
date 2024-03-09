package com.ivan.knowledgebase.user.acceptance.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public enum BlackboxJerseyClient {
    INSTANCE;

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

        return ClientBuilder.newClient(config)
                .register(new JacksonJaxbJsonProvider(objectMapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));
    }

    private static HttpClientConnectionManager createConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(500);
        connectionManager.setDefaultMaxPerRoute(100);

        return connectionManager;
    }

}
