#!/usr/bin/env bash

pathToTheCheckstyleReport="$PROJECT_DIR/_knowledgebase-multimodule/target/checkstyle-result.xml"

mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/pom.xml \
    resources:resources@copy-checkstyle-configuration-files \
    checkstyle:checkstyle-aggregate \
    -Dcheckstyle.enableExternalDtdLoad=true

mvnReturnCode=$?

if [ -e $pathToTheCheckstyleReport ]; then
    echo "The report is accessible through this path: $pathToTheCheckstyleReport"
else
    echo "Was not possible to generate the report"
fi

exit $mvnReturnCode
