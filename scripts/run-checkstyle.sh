#!/usr/bin/env bash

pathToTheCheckstyleReport="$PROJECT_DIR/knowledge-base-multimodule/target/checkstyle-result.xml"

mvnw -f $PROJECT_DIR/knowledge-base-multimodule/pom.xml checkstyle:checkstyle-aggregate

mvnReturnCode=$?

if [ -e $pathToTheCheckstyleReport ]; then
    echo "The report is accessible through this path: $pathToTheCheckstyleReport"
else
    echo "Was not possible to generate the report"
fi

exit $mvnReturnCode
