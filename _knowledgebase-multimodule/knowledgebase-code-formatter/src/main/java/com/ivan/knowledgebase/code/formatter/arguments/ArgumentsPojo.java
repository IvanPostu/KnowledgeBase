package com.ivan.knowledgebase.code.formatter.arguments;

public final class ArgumentsPojo {

    private final boolean parallel;
    private final boolean applyFormatting;
    private final String baseDirectoryPath;

    public ArgumentsPojo(boolean parallel, boolean applyFormatting, String baseDirectoryPath) {
        this.parallel = parallel;
        this.applyFormatting = applyFormatting;
        this.baseDirectoryPath = baseDirectoryPath;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getApplyFormatting() {
        return applyFormatting;
    }

    public String getBaseDirectoryPath() {
        return baseDirectoryPath;
    }
}
