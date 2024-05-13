package com.ivan.knowledgebase.code.formatter.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.core.JavaCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.ivan.knowledgebase.code.formatter.arguments.ProgramArgumentsProvider;

// TODO: to refactor and to split to multiple classes
public enum ConfigurationProvider {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationProvider.class);
    private static final String DEFAULT_FORMATTER_CONFIG = "Eclipse-built-in.xml";
    private static final Map<String, String> DEFAULT_CONFIGURATION =
        readDefaultXmlConfigurationFileFromClasspath(DEFAULT_FORMATTER_CONFIG);

    public static ConfigurationProvider getInstance() {
        return INSTANCE;
    }

    public Map<String, String> provideConfiguration() {
        LOG.info("Explicit path to configuration file is not specified, using default: {}",
            DEFAULT_FORMATTER_CONFIG);
        return DEFAULT_CONFIGURATION;
    }

    public Map<String, String> provideConfiguration(String configurationFilePath) {
        try {
            return internalProvideConfiguration(configurationFilePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<String, String> internalProvideConfiguration(String configurationFilePath) throws IOException {
        Map<String, String> explicitConfiguration = readConfiguration(configurationFilePath);
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

    private Map<String, String> readConfiguration(String pathToFile) {
        try {
            return Collections.unmodifiableMap(readXmlConfigurationFile(pathToFile, ConfigurationSource.FILESYSTEM));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.error("Was not possible to read configuration file: {} due to the error ", pathToFile, e);
            throw new IllegalStateException(e);
        }
    }

    private static Map<String, String> readDefaultXmlConfigurationFileFromClasspath(String fileName) {
        try {
            return Collections.unmodifiableMap(readXmlConfigurationFile(fileName, ConfigurationSource.CLASSPATH));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Map<String, String> readXmlConfigurationFile(
        String pathToFile,
        ConfigurationSource configurationSource)
        throws ParserConfigurationException, IOException, SAXException {

        Objects.requireNonNull(pathToFile);

        Map<String, String> result = new LinkedHashMap<String, String>();
        try (InputStream inputStream = ConfigurationSource.CLASSPATH == configurationSource
            ? ConfigurationProvider.class.getClassLoader().getResourceAsStream(pathToFile)
            : new FileInputStream(new File(pathToFile))) {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.parse(inputStream);
            document.getDocumentElement().normalize();

            org.w3c.dom.NodeList nodeList = document.getElementsByTagName("setting");
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Node node = nodeList.item(i);
                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    String id = element.getAttribute("id");
                    String value = element.getAttribute("value");
                    result.put(id, value);
                }
            }

        }

        return Collections.unmodifiableMap(result);
    }

    private enum ConfigurationSource {
        CLASSPATH, FILESYSTEM
    }
}
