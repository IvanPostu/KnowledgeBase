package com.ivan.knowledgebase.code.formatter.arguments;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class ProgramArgumentsProvider {
    private final String[] args;

    public ProgramArgumentsProvider(String... args) {
        this.args = args;
    }

    public ArgumentsPojo provide() {
        Options options = configureOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            System.out.println(e.getMessage());
            formatter.printHelp("Application", options);
            System.exit(1);
        }

        String applyFormattingValue = cmd.getOptionValue("applyFormatting");

        int threadsCount = parseThreadCount(cmd.getOptionValue("threadsCount"));
        String baseDirectoryPath = cmd.hasOption("baseDirectoryPath")
            ? cmd.getOptionValue("baseDirectoryPath")
            : new File("").getAbsolutePath();

        ArgumentsPojo argumentsPojo = new ArgumentsPojo(
            threadsCount,
            "true".equalsIgnoreCase(applyFormattingValue),
            baseDirectoryPath);

        logArguments(argumentsPojo);

        return argumentsPojo;
    }

    private int parseThreadCount(String threadCountValue) {
        try {
            int value = Integer.valueOf(threadCountValue).intValue();
            return value > 0 ? value : 1;
        } catch (Exception e) {
            return 1;
        }
    }

    private void logArguments(ArgumentsPojo argumentsPojo) {
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = argumentsPojo.getClass();

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
                try {
                    String fieldName = method.getName().substring(3);
                    fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                    Object value = method.invoke(argumentsPojo);
                    map.put(fieldName, String.valueOf(value));
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        System.out.println(String.format("Arguments: %s", map));
    }

    private Options configureOptions() {
        Option parallelOption = Option.builder()
            .longOpt("threadsCount")
            .argName("threadsCount")
            .hasArg()
            .required(false)
            .desc("threads count")
            .build();
        Option applyFormattingOption = Option.builder()
            .longOpt("applyFormatting")
            .argName("applyFormatting")
            .hasArg()
            .required(false)
            .desc("flag to apply format changes")
            .build();
        Option baseDirectoryPathOption = Option.builder()
            .longOpt("baseDirectoryPath")
            .argName("baseDirectoryPath")
            .hasArg()
            .required(false)
            .desc("absolute path to base directory which should be scanned recursively")
            .build();

        return new Options()
            .addOption(parallelOption)
            .addOption(applyFormattingOption)
            .addOption(baseDirectoryPathOption);
    }
}
