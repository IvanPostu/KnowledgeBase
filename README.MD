# Knowledge base

A standalone web server with a set of utilities intended to keep notes and information in a well structured format with a possibility to search and dive into it in an efficient way.

## Useful commands
```sh
# Run nix-shell with default jdk8
$ nix-shell --run bash

# Run nix-shell with jdk17
$ nix-shell ./default.nix --argstr jdkVersion 17 --run bash

# Package artifact
$ run-rebuild-multimodule.sh

# Run 
$ run-rebuild-multimodule.sh  && java -jar $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-server-embedded/target/knowledgebase-server-embedded.jar
# Or
$ run-rebuild-multimodule.sh  && run-exec-embedded-server.sh

# Init project for eclipse
$ mvnw clean eclipse:clean eclipse:eclipse

# Show tree of dependencies
$ mvnw  dependency:tree -Ddetail=true

# Create a module
$ mvnw archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DgroupId={package.name} -DartifactId={snake-case-name}

# Increment project versions
$ mvnw versions:set -DnewVersion=1.0.4

# Build project (tests + checkstyle)
# -DskipTests -> skip tests execution
# -Dmaven.test.skip=true -> skip tests compilation
# -Dcheckstyle.skip=true -> skip checkstyle
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean install

# Install without tests & checkstyle
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean install -DskipTests -Dcheckstyle.skip=true

# Run tests (acceptance tests are ignored)
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean test
# Run only checkstyle for all modules
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean checkstyle:checkstyle-aggregate

# Checkstyle apply changes globally
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-checkstyle/pom.xml clean  install

# Run checkstyle + unit tests
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean verify

# Generate report for tests + checkstyle (acceptance tests are ignored)
# Result path: /home/ivan/Projects/KnowledgeBase/_knowledgebase-multimodule/target/staging/
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean site:site site:stage

# Run user acceptance tests
# Result path: /home/ivan/Projects/KnowledgeBase/_knowledgebase-multimodule/knowledgebase-user-acceptance-test/target/surefire-reports/
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-user-acceptance-test/pom.xml clean test -Puser-acceptance-tests

# Run code formatter check
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-code-formatter/pom.xml clean install && java -jar _knowledgebase-multimodule/knowledgebase-code-formatter/target/knowledgebase-code-formatter.jar --threadsCount 10 --applyFormatting true --baseDirectoryPath=$PROJECT_DIR/_knowledgebase-multimodule --logLevel INFO 

$ $ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-code-formatter/pom.xml clean install && java -jar _knowledgebase-multimodule/knowledgebase-code-formatter/target/knowledgebase-code-formatter.jar --help
```

## How to supress checkstyle rules
```java
    // CHECKSTYLE.SUPPRESS: MagicNumber
    private int number = 32;

    // CHECKSTYLE.OFF: MagicNumber
    private int number2 = 32;
    // CHECKSTYLE.ON: MagicNumber

    private int number3 = 32;
```

## How to run TestNG in eclipse
```sh
# 1. Install extension
# 2. Change module's java version to the value which is greater than 8, e.g. 17
```


## References

- 