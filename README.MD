# Knowledge base

A standalone web server with a set of utilities intended to keep notes and information in a well structured format with a possibility to search and dive into it in an efficient way.

## Useful commands
```sh
$ nix-shell --run bash

$ mvnw clean eclipse:clean eclipse:eclipse

# Package artifact
$ cd knowledge-base-standalone/
$ mvnw package
$ java -jar $PROJECT_DIR/knowledge-base-multimodule/knowledge-base-server-embedded/target/knowledge-base-server-embedded.jar

# Run 
$ cd $PROJECT_DIR/knowledge-base-multimodule
$ mvnw package
$ cd $PROJECT_DIR/knowledge-base-multimodule/knowledge-base-server-embedded
$ mvnw package exec:java
```