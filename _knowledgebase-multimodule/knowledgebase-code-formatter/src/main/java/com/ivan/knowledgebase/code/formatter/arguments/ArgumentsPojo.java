package com.ivan.knowledgebase.code.formatter.arguments;

public final class ArgumentsPojo {

    private final int threadsCount;
    private final boolean applyFormatting;
    private final String baseDirectoryPath;

    public ArgumentsPojo(int threadsCount, boolean applyFormatting, String baseDirectoryPath) {
        this.threadsCount = threadsCount;
        this.applyFormatting = applyFormatting;
        this.baseDirectoryPath = baseDirectoryPath;
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
}
