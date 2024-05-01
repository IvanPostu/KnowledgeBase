package com.ivan.knowledgebase.code.formatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public enum FormatterValidator {
    INSTANCE;

    public static FormatterValidator getInstance() {
        return INSTANCE;
    }

    public void validateFormatting(List<File> files, Map<String, String> formatterConfiguration, int threadsCount) {
        AtomicInteger countOfUnformattedFiles = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        Queue<File> filesQueue = new ConcurrentLinkedQueue<File>(files);

        for (int i = 0; i < threadsCount; i++) {
            executor.submit(() -> {
                CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(formatterConfiguration);
                File file = null;
                while ((file = filesQueue.poll()) != null) {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
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
                                System.out.println(
                                    String.format("File: %s is not properly formatted", file.getAbsolutePath()));
                                countOfUnformattedFiles.incrementAndGet();
                            }
                        }

                    }
                }
            });
        }
        try {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.MINUTES);
            System.out.println(String.format("Total unformatted files: %s", countOfUnformattedFiles.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

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

}
