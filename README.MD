# Knowledge base

A standalone web server with a set of utilities intended to keep notes and information in a well structured format with a possibility to search and dive into it in an efficient way.

## Useful commands
```sh
$ nix-shell --run bash

# Package artifact
$ run-rebuild-multimodule.sh

# Run 
$ run-rebuild-multimodule.sh  && java -jar $PROJECT_DIR/_knowledge-base-multimodule/knowledge-base-server-embedded/target/knowledge-base-server-embedded.jar
# Or
$ run-rebuild-multimodule.sh  && run-exec-embedded-server.sh

# Init project for eclipse
$ mvnw clean eclipse:clean eclipse:eclipse

# Show tree of dependencies
$ mvnw  dependency:tree -Ddetail=true

# Create a module
$ mvnw archetype:generate -DgroupId=package.name -DartifactId=snake-case-name

# Increment project versions
$ mvnw versions:set -DnewVersion=1.0.4

# Build project 
$ mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/ clean install

# Generate report for tests + checkstyle
# Result path: /home/ivan/Projects/KnowledgeBase/_knowledge-base-multimodule/target/staging/
$ mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/pom.xml clean site:site site:stage

# Run user acceptance tests
# Result path: /home/ivan/Projects/KnowledgeBase/_knowledge-base-multimodule/knowledge-base-user-acceptance-test/target/surefire-reports/
$ mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/knowledge-base-user-acceptance-test/pom.xml clean test -Puser-acceptance-tests
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