#!/usr/bin/env bash

set -e

mvnw -f $PROJECT_DIR/knowledge-base-multimodule/pom.xml clean install
