#!/usr/bin/env bash

set -e

mvnw -f $PROJECT_DIR/knowledge-base-multimodule/knowledge-base-server-embedded/pom.xml clean install 
