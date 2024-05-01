package com.ivan.knowledgebase.code.formatter.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.core.JavaCore;
import org.xml.sax.SAXException;

public enum ConfigurationProvider {
    INSTANCE;

    public static ConfigurationProvider getInstance() {
        return INSTANCE;
    }

    public Map<String, String> provideConfiguration() {
        try {
            return internalProvideConfiguration();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<String, String> internalProvideConfiguration() throws IOException {
        Map<String, String> explicitConfiguration = readDefaultXmlConfigurationFileFromClasspath("formats.xml");
        Map<String, String> formatterDefaultConfiguration = getDefaultFormatterConfiguration();

        Map<String, String> finalConfiguration = new LinkedHashMap<String, String>();
        finalConfiguration.putAll(formatterDefaultConfiguration);

        List<String> unknownConfigurationKeys = new LinkedList<String>();
        explicitConfiguration.forEach((key, value) -> {
            if (!finalConfiguration.containsKey(key)) {
                unknownConfigurationKeys.add(key);
            }
            finalConfiguration.put(key, value);
        });

        if (!unknownConfigurationKeys.isEmpty()) {
            throw new IllegalStateException("Formatter configuration has unknown fields: " + unknownConfigurationKeys
                .stream().collect(Collectors.joining(",\n\t")));
        }

        return Collections.unmodifiableMap(finalConfiguration);
    }

    private Map<String, String> getDefaultFormatterConfiguration()
        throws IOException {
        Map<String, String> options = new LinkedHashMap<String, String>(JavaCore.getOptions());
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);

        return Collections.unmodifiableMap(options);
    }

    private Map<String, String> readDefaultXmlConfigurationFileFromClasspath(String fileName)
        throws IOException {
        Map<String, String> result = new LinkedHashMap<String, String>();
        try (InputStream inputStream = ConfigurationProvider.class.getClassLoader().getResourceAsStream(fileName)) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            org.w3c.dom.NodeList nodeList = doc.getElementsByTagName("setting");
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Node node = nodeList.item(i);
                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    String id = element.getAttribute("id");
                    String value = element.getAttribute("value");
                    result.put(id, value);
                }
            }

        } catch (ParserConfigurationException | SAXException e) {
            throw new IllegalStateException(e);
        }

        return Collections.unmodifiableMap(result);
    }
}
