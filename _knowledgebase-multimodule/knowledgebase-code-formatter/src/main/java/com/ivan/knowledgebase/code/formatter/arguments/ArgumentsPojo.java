package com.ivan.knowledgebase.code.formatter.arguments;

import java.util.Optional;

public final class ArgumentsPojo {

    private final int threadsCount;
    private final boolean applyFormatting;
    private final String baseDirectoryPath;
    private final ch.qos.logback.classic.Level logLevel;
    private final boolean help;
    private final Optional<String> configurationFilePath;

    public ArgumentsPojo(int threadsCount, boolean applyFormatting, String baseDirectoryPath,
        ch.qos.logback.classic.Level logLevel, boolean help,
        String configurationFilePath) {
        this.threadsCount = threadsCount;
        this.applyFormatting = applyFormatting;
        this.baseDirectoryPath = baseDirectoryPath;
        this.logLevel = logLevel;
        this.help = help;
        this.configurationFilePath = Optional.ofNullable(configurationFilePath);
    }

    public int getThreadsCount() {
        return threadsCount;
    }

    public boolean getApplyFormatting() {
        return applyFormatting;
    }

    public String getBaseDirectoryPath() {
        return baseDirectoryPath;
    }

    public ch.qos.logback.classic.Level getLogLevel() {
        return logLevel;
    }

    public boolean getHelp() {
        return help;
    }

    public Optional<String> getConfigurationFilePath() {
        return configurationFilePath;
    }
}
