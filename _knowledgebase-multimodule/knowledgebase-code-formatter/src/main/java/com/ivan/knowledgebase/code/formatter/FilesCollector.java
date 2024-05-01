package com.ivan.knowledgebase.code.formatter;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public final class FilesCollector {

    public List<File> collectJavaFilesFromDirectory(String directoryPath) {
        List<File> files = findFilesRecursively(new File(directoryPath),
            absolutePath -> absolutePath.endsWith(".java"));
        return Collections.unmodifiableList(files);
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
