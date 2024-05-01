package com.ivan.knowledgebase.code.formatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivan.knowledgebase.code.formatter.FilesCollector.FileElement;

public enum FormatterValidator {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(FormatterValidator.class);

    public static FormatterValidator getInstance() {
        return INSTANCE;
    }

    public void applyFormatting(List<FileElement> files, Map<String, String> formatterConfiguration, int threadsCount) {
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        Queue<FileElement> filesQueue = new ConcurrentLinkedQueue<FileElement>(files);

        for (int i = 0; i < threadsCount; i++) {
            executor.submit(() -> {
                CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(formatterConfiguration);
                FileElement fileElement = null;
                while ((fileElement = filesQueue.poll()) != null) {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    String contentAsString = new String(readFile(fileElement.getFile()));
                    TextEdit edit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, contentAsString, 0,
                        contentAsString.length(), 0, null);
                    if (edit == null) {
                        continue;
                    }

                    Document document = new Document(contentAsString);
                    if (applyAndValidate(edit, document)) {
                        String formattedContent = document.get();
                        if (!formattedContent.equals(contentAsString)) {
                            try (FileWriter writer = new FileWriter(fileElement.getFile())) {

                            } catch (IOException e) {
                                LOG.warn("Was unable to change the file {}", fileElement.getTrimmedAbsoluteName(), e);
                            }
                        }
                    } else {
                        LOG.warn("File: ${} is malformed", fileElement.getTrimmedAbsoluteName());
                    }

                }
            });
        }
        try {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void validateFormatting(List<FileElement> files, Map<String, String> formatterConfiguration,
        int threadsCount) {
        AtomicInteger countOfUnformattedFiles = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        Queue<FileElement> filesQueue = new ConcurrentLinkedQueue<FileElement>(files);

        for (int i = 0; i < threadsCount; i++) {
            executor.submit(() -> {
                CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(formatterConfiguration);
                FileElement fileElement = null;
                while ((fileElement = filesQueue.poll()) != null) {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    String contentAsString = new String(readFile(fileElement.getFile()));
                    TextEdit edit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, contentAsString, 0,
                        contentAsString.length(), 0, null);
                    if (edit == null) {
                        countOfUnformattedFiles.incrementAndGet();
                        continue;
                    }
                    Document document = new Document(contentAsString);
                    if (!applyAndValidate(edit, document)) {
                        LOG.error("File: {} is malformed", fileElement.getTrimmedAbsoluteName());
                        countOfUnformattedFiles.incrementAndGet();
                    } else {
                        String formattedContent = document.get();
                        if (!formattedContent.equals(contentAsString)) {
                            LOG.error("File: {} is not properly formatted", fileElement.getTrimmedAbsoluteName());
                            countOfUnformattedFiles.incrementAndGet();
                        }
                    }
                }
            });
        }
        try {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.MINUTES);
            LOG.info(String.format("Total unformatted files: %s", countOfUnformattedFiles.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (countOfUnformattedFiles.get() > 0) {
            System.exit(1);
        }
    }

    private boolean applyAndValidate(TextEdit textEdit, Document document) {
        try {
            textEdit.apply(document);
            return true;
        } catch (MalformedTreeException | BadLocationException e) {
            return false;
        }
    }

    private byte[] readFile(File file) {
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
