package com.ivan.knowledgebase.user.acceptance.test;

import java.net.URI;
import java.util.Collections;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

import com.ivan.knowledgebase.common.rest.WebResourceFactory;
import com.ivan.knowledgebase.common.rest.exception.translation.RestExceptionTranslationStrategy;

public final class Blackbox<ENDPOINT> {
    private final Class<ENDPOINT> endpointInterface;
    private final EndpointsConfig endpointsConfig;

    private URI baseUri;
    private RestExceptionTranslationStrategy restExceptionTranslationStrategy = new RestExceptionTranslationStrategy() {
    };

    private Blackbox(Class<ENDPOINT> endpointInterface) {
        this.endpointInterface = endpointInterface;
        this.endpointsConfig = new EndpointsConfig();
    }

    public static <ENDPOINT> Blackbox<ENDPOINT> createEndpoint(Class<ENDPOINT> endpointInterface) {
        return new Blackbox<ENDPOINT>(endpointInterface);
    }

    public Blackbox<ENDPOINT> forKnowledgeBaseApi() {
        this.baseUri = URI.create(endpointsConfig.getApiUrl());
        return this;
    }

    public ENDPOINT build() {
        Client client = BlackboxJerseyClient.INSTANCE.getClient();
        WebTarget webTarget = client.target(baseUri);

        return WebResourceFactory.newResource(endpointInterface, webTarget, false,
            new MultivaluedHashMap<String, Object>(), Collections.emptyList(), new Form());
    }

}
