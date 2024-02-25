package com.ivan.knowledgebase.server.embedded;

public class Main {
	public static void main(String[] args) throws Exception {
		EmbeddedServer.run(args, "META-INF/resources");
	}
}
