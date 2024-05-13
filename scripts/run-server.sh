#!/usr/bin/env bash

set -e

mvnw -f \
    $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-server-embedded/pom.xml \
    exec:java \
    -Dexec.args="--workspaceDir $PROJECT_DIR/tmp"
