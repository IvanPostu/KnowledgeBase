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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivan.knowledgebase.code.formatter.FormatterValidator;

public final class ProgramArgumentsProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ProgramArgumentsProvider.class);

    private final String[] args;
    private final Options options;

    public ProgramArgumentsProvider(String... args) {
        this.args = args;
        this.options = configureOptions();
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Knowledge Base code formatter", options);
    }

    public ArgumentsPojo provide() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            LOG.error("CLI arguments parse error", e);
            System.exit(1);
        }

        String applyFormattingValue = cmd.getOptionValue("applyFormatting");

        String configurationFileValue = cmd.getOptionValue("configurationFile");

        int threadsCount = parseThreadCount(cmd.getOptionValue("threadsCount"));
        String baseDirectoryPath = cmd.hasOption("baseDirectoryPath")
            ? cmd.getOptionValue("baseDirectoryPath")
            : new File("").getAbsolutePath();
        ch.qos.logback.classic.Level logLevel = parseLogLevel(cmd.getOptionValue("logLevel"));

        ArgumentsPojo argumentsPojo = new ArgumentsPojo(
            threadsCount,
            "true".equalsIgnoreCase(applyFormattingValue),
            baseDirectoryPath,
            logLevel,
            cmd.hasOption("help"),
            configurationFileValue);

        logArguments(argumentsPojo);

        return argumentsPojo;
    }

    private ch.qos.logback.classic.Level parseLogLevel(String value) {
        try {
            return ch.qos.logback.classic.Level.valueOf(value);
        } catch (Exception e) {
            return ch.qos.logback.classic.Level.INFO;
        }
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

        LOG.info(String.format("Arguments: %s", map));
    }

    private Options configureOptions() {
        Option helpOption = Option.builder("h")
            .longOpt("help")
            .desc("print help message")
            .build();
        Option logLevelOption = Option.builder()
            .longOpt("logLevel")
            .argName("logLevel")
            .hasArg()
            .required(false)
            .desc("log level")
            .build();
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
        Option configurationFileOption = Option.builder()
            .longOpt("configurationFile")
            .argName("configurationFile")
            .hasArg()
            .required(false)
            .desc("eclipse formatter configuration file, e.g. 'formats.xml'")
            .build();

        return new Options()
            .addOption(helpOption)
            .addOption(logLevelOption)
            .addOption(parallelOption)
            .addOption(applyFormattingOption)
            .addOption(baseDirectoryPathOption)
            .addOption(configurationFileOption);
    }
}
