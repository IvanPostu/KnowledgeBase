package com.ivan.knowledgebase.code.formatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

public class Main {

	public static void main(String[] args) throws Exception {
		String directoryPath = "/home/ivan/Projects/KnowledgeBase/_knowledgebase-multimodule";

		List<File> files = findFilesRecursively(new File(directoryPath),
				absolutePath -> absolutePath.endsWith(".java"));

		// Configure the formatter options
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);

		CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(options);

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
					int p = 909;
				}

				int i = 909;

			}

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
