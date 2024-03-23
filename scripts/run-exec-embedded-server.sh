#!/usr/bin/env bash

set -e

mvnw -f $PROJECT_DIR/_knowledge-base-multimodule/knowledge-base-server-embedded/pom.xml exec:java
