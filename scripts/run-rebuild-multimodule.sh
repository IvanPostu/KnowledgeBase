#!/usr/bin/env bash

set -e

mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/pom.xml clean install
