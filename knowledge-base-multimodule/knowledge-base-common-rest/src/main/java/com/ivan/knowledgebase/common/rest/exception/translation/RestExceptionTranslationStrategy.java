package com.ivan.knowledgebase.common.rest.exception.translation;

import java.util.List;

import javax.ws.rs.ClientErrorException;

public interface RestExceptionTranslationStrategy {

    default Exception translateException(List<Class<? extends Exception>> expectedExceptionTypes,
            ClientErrorException clientRestException) {
        return clientRestException;
    }

}
