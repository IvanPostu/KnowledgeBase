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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import com.ivan.knowledgebase.code.formatter.arguments.ArgumentsPojo;
import com.ivan.knowledgebase.code.formatter.arguments.ProgramArgumentsProvider;
import com.ivan.knowledgebase.code.formatter.config.ConfigurationProvider;

public class Main {

    public static void main(String[] args) throws Exception {
        ArgumentsPojo arguments = new ProgramArgumentsProvider(args).provide();
        String directoryPath = arguments.getBaseDirectoryPath();

        List<File> files = findFilesRecursively(new File(directoryPath),
            absolutePath -> absolutePath.endsWith(".java"));

        Map<String, String> formatterConfiguration = ConfigurationProvider.getInstance().provideConfiguration();
        validateFormatting(files, formatterConfiguration, arguments.getParallel());
    }

    private static void validateFormatting(List<File> files, Map<String, String> formatterConfiguration,
        boolean parallel) {

        AtomicInteger countOfUnformattedFiles = new AtomicInteger(0);

        Stream<File> stream = files.stream();
        stream = parallel ? stream.parallel() : stream;

        stream.forEach(file -> {
            // TODO to use thread pool and to use N code formatters where N is the number of threads
            CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(formatterConfiguration);
            String contentAsString = new String(readFile(file));

            TextEdit edit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, contentAsString, 0,
                contentAsString.length(), 0, null);
            if (edit != null) {
                Document document = new Document(contentAsString);
                if (!applyAndValidate(edit, document)) {
                    System.out.println(String.format("File: %s is malformed", file.getAbsolutePath()));
                    countOfUnformattedFiles.incrementAndGet();
                } else {
                    String formattedContent = document.get();
                    if (!formattedContent.equals(contentAsString)) {
                        System.out.println(String.format("File: %s is not properly formatted", file.getAbsolutePath()));
                        countOfUnformattedFiles.incrementAndGet();
                    }
                }

            }
        });
        System.out.println(String.format("Total unformatted files: %s", countOfUnformattedFiles.get()));

        if (countOfUnformattedFiles.get() > 0) {
            System.exit(1);
        }
    }

    private static boolean applyAndValidate(TextEdit textEdit, Document document) {
        try {
            textEdit.apply(document);
            return true;
        } catch (MalformedTreeException | BadLocationException e) {
            return false;
        }
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
}
