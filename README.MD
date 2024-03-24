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
$ mvnw archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DgroupId={package.name} -DartifactId={snake-case-name}

# Increment project versions
$ mvnw versions:set -DnewVersion=1.0.4

# Build project (tests + checkstyle)
# -DskipTests -> skip tests execution
# -Dmaven.test.skip=true -> skip tests compilation
# -Dcheckstyle.skip=true -> skip checkstyle
$ mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/ clean install

# Install without tests & checkstyle
$ mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/pom.xml clean install -DskipTests -Dcheckstyle.skip=true

# Run tests (acceptance tests are ignored)
$ mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/pom.xml clean test
# Run checkstyle for all modules
$ mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/pom.xml clean checkstyle:checkstyle-aggregate

# Generate report for tests + checkstyle (acceptance tests are ignored)
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

## How to run testng in eclipse
```sh
# 1. Install extension
# 2. Change module's java version to the value which is greater than 8, e.g. 17
```


## References

#### Markdown demo
- [Marked Demo](https://marked.js.org/demo/)
- [CommonMark Demo](https://spec.commonmark.org/dingus/)
- [Daring Fireball (pedantic) Demo](https://daringfireball.net/projects/markdown/dingus)