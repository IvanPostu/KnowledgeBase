#!/usr/bin/env bash

set -e

mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml clean install
