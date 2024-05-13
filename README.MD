# Knowledge Base
This Java project provides a standalone, embedded web server optimized for fast operations with text data stored in Markdown format. Designed to efficiently parse and serve Markdown files.

## Setup
In order to easily provide required environment variables, cli tools and dependencies for all platforms is used `nix-shell`.

### Setup and startup (for development)
1. `nix-shell ./default.nix --run bash`
   - *(Optional) To explicitly set `JAVA_HOME` to jdk17, execute `nix-shell ./default.nix --argstr jdkVersion 17 --run bash`
   - *(Optional) For eclipse run `mvnw clean eclipse:clean eclipse:eclipse`
2. Run server side application in development mode: 
   ```sh
   # Default port 8080, workspaceDir ./tmp
   # Debugger can be attached
   $ run-server.sh
   ```
3. Run client side application in development mode: 
   ```sh
   # TODO
   ```

### Package artifact (for release)
```sh
#   TODO: 
# - build client side
# - build server side
# - package everything in JAR file
```

## Other

### Project formatter

#### Build JAR and run 
```sh
$ JAVA_HOME=$JAVA_HOME_17 mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-code-formatter/pom.xml clean install && $JAVA_HOME_17/bin/java -jar _knowledgebase-multimodule/knowledgebase-code-formatter/target/knowledgebase-code-formatter.jar --help
```

#### Run inline (with project configuration)
```sh
$ JAVA_HOME=$JAVA_HOME_17 mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-code-formatter/pom.xml exec:java -Dexec.args="--threadsCount 10 --applyFormatting false --baseDirectoryPath=$PROJECT_DIR/_knowledgebase-multimodule --logLevel INFO --configurationFile $PROJECT_DIR/formats.xml"
```

### Maven
```sh
# Increment all projects versions
$ mvnw versions:set -DnewVersion=1.0.4

# Build project (tests + check-style)
# -DskipTests -> skip tests execution
# -Dmaven.test.skip=true -> skip tests compilation
# -Dcheckstyle.skip=true -> skip checkstyle
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean install

# Run only checkstyle for all modules
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean checkstyle:checkstyle-aggregate

# Checkstyle internal project install globally
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-checkstyle/pom.xml clean install

# Generate report for tests + checkstyle (acceptance tests are ignored)
# Result path: $PROJECT_DIR/_knowledgebase-multimodule/target/staging/
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean site:site site:stage

# Run user acceptance tests
# Result path: $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-user-acceptance-test/target/surefire-reports/
$ mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-user-acceptance-test/pom.xml clean test -Puser-acceptance-tests

# Show tree of dependencies
$ mvnw  dependency:tree -Ddetail=true

# Create a module
$ mvnw archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DgroupId={package.name} -DartifactId={snake-case-name}
```
### How to suppress checkstyle rules
```java
    // CHECKSTYLE.SUPPRESS: MagicNumber
    private int number = 32;

    // CHECKSTYLE.OFF: MagicNumber
    private int number2 = 32;
    // CHECKSTYLE.ON: MagicNumber

    private int number3 = 32;
```

### How to run TestNG in eclipse
```sh
# 1. Install TestNG extension
# 2. Change module's java version to the value which is greater than 8, e.g. 17
```
