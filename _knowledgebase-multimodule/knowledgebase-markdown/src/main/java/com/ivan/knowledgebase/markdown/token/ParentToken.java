package com.ivan.knowledgebase.markdown.token;

import java.util.List;

public interface ParentToken {

    List<Token> getChildTokens();

}
