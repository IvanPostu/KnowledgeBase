package com.ivan.knowledgebase.code.formatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.LoggerFactory;

import com.ivan.knowledgebase.code.formatter.FilesCollector.FileElement;
import com.ivan.knowledgebase.code.formatter.arguments.ArgumentsPojo;
import com.ivan.knowledgebase.code.formatter.arguments.ProgramArgumentsProvider;
import com.ivan.knowledgebase.code.formatter.config.ConfigurationProvider;

import ch.qos.logback.classic.Level;

public class Main {
    private static final FilesCollector FILES_COLLECTOR = new FilesCollector();

    public static void main(String[] args) throws Exception {
        ArgumentsPojo arguments = new ProgramArgumentsProvider(args).provide();
        resetLogLevel(arguments.getLogLevel());

        String directoryPath = arguments.getBaseDirectoryPath();
        List<FileElement> files = FILES_COLLECTOR.collectJavaFilesFromDirectory(directoryPath);
        Map<String, String> formatterConfiguration = ConfigurationProvider.getInstance().provideConfiguration();

        FormatterValidator.getInstance()
            .validateFormatting(files, formatterConfiguration, arguments.getThreadsCount());
    }

    private static void resetLogLevel(Level logLevel) {
        ch.qos.logback.classic.Logger rootLogger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(logLevel);
    }
}
