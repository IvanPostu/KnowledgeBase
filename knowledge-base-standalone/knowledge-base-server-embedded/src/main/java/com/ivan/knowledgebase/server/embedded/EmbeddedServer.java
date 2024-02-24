package com.ivan.knowledgebase.server.embedded;

import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbeddedServer {
	public static void run(String[] args, String resourcesPath) throws Exception {
		Server server = new Server(8080);
		WebAppContext webAppContext = new WebAppContext();
		server.setHandler(webAppContext);

		URL webAppDir = EmbeddedServer.class.getClassLoader().getResource(resourcesPath);
		webAppContext.setResourceBase(webAppDir.toURI().toString());

		webAppContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				".*/target/classes/|.*\\.jar");

		server.start();
		System.out.println("Server started!");

		server.join();
	}
}
