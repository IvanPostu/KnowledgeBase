package com.ivan.knowledgebase.shared;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public enum ToString {
    INSTANCE;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .setVisibility(VisibilityChecker.Std.defaultInstance()
                    .withFieldVisibility(Visibility.ANY));

    public String create(Object object) {
        try {
            return OBJECT_MAPPER
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
