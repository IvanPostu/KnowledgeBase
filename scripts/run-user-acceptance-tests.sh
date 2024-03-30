#!/usr/bin/env bash

pathToTheReport="$PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-user-acceptance-test/target/site/index.html"

mvnw -f $PROJECT_DIR/_knowledgebase-multimodule/knowledgebase-user-acceptance-test/pom.xml \
    clean site \
    -Puser-acceptance-tests

mvnReturnCode=$?

if [ -e $pathToTheReport ]; then
    echo "The report is accessible through this path: $pathToTheReport"
else
    echo "Was not possible to generate the report"
fi

exit $mvnReturnCode
