package com.ivan.knowledgebase.code.formatter;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public final class FilesCollector {

    public List<FileElement> collectJavaFilesFromDirectory(String directoryPath) {
        List<File> files = findFilesRecursively(new File(directoryPath),
            absolutePath -> absolutePath.endsWith(".java"));
        UnaryOperator<String> fileDirectoryPathTrimmer = value -> value.replaceFirst(directoryPath, "");

        return files.stream()
            .map(file -> new FileElement(file, fileDirectoryPathTrimmer.apply(file.getAbsolutePath())))
            .collect(Collectors.toUnmodifiableList());
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

    public static final class FileElement {
        private final File file;
        private final String trimmedAbsoluteName;

        public FileElement(File file, String trimmedAbsoluteName) {
            this.file = file;
            this.trimmedAbsoluteName = trimmedAbsoluteName;
        }

        public File getFile() {
            return file;
        }

        public String getTrimmedAbsoluteName() {
            return trimmedAbsoluteName;
        }

    }
}
