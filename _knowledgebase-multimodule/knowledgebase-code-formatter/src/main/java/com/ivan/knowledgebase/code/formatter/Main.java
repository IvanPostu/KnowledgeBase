package com.ivan.knowledgebase.code.formatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws Exception {
        String directoryPath = "/home/ivan/Projects/KnowledgeBase/_knowledgebase-multimodule";

        List<File> files = findFilesRecursively(new File(directoryPath),
            absolutePath -> absolutePath.endsWith(".java"));

        Map<String, String> formatterConfiguration = ConfigurationProvider.getInstance().provideConfiguration();
        CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(formatterConfiguration);

        int countOfUnformattedFiles = 0;
        for (File file : files) {
            byte[] content = readFile(file);
            String contentAsString = new String(content);

            TextEdit edit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, contentAsString, 0,
                contentAsString.length(), 0, null);
            if (edit != null) {
                Document document = new Document(contentAsString);
                edit.apply(document);
                String formattedContent = document.get();

                if (!formattedContent.equals(contentAsString)) {
                    System.out.println(String.format("File: %s is not properly formatted", file.getAbsolutePath()));
                    countOfUnformattedFiles++;
                }

            }

        }
        System.out.println(String.format("Total unformatted files: %s", countOfUnformattedFiles));

        if (countOfUnformattedFiles > 0) {
            System.exit(1);
        }
    }

    private static Map<String, String> getGlobalConfiguration(Map<String, String> configEntriesFromFile)
        throws IOException {
        Map<String, String> options = new LinkedHashMap<String, String>(JavaCore.getOptions());
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);

        List<String> unknownConfigurationKeys = new LinkedList<String>();
        configEntriesFromFile.forEach((key, value) -> {
            if (!options.containsKey(key)) {
                unknownConfigurationKeys.add(key);
            }
            options.put(key, value);
        });

        if (!unknownConfigurationKeys.isEmpty()) {
            throw new IllegalStateException("Formatter configuration has unknown fields: " + unknownConfigurationKeys
                .stream().collect(Collectors.joining(",\n\t")));
        }

        return Collections.unmodifiableMap(options);
    }

    private static byte[] readFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[] {};
        }
    }

    private static List<File> findFilesRecursively(File directory, Predicate<String> absolutePathPredicate) {
        List<File> result = new LinkedList<>();

        File[] files = directory.listFiles();

        if (files == null) {
            return Collections.emptyList();
        }

        for (File file : files) {
            if (file.isDirectory()) {
                List<File> nestedFiles = findFilesRecursively(file, absolutePathPredicate);
                result.addAll(nestedFiles);
            } else {
                if (absolutePathPredicate.test(file.getAbsolutePath())) {
                    result.add(file);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    private static byte[] readInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[2048];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }
}
