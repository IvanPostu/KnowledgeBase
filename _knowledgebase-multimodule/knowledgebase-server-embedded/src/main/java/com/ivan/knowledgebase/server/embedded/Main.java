package com.ivan.knowledgebase.server.embedded;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.URL;

public class Main {
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = new Server(DEFAULT_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ResourceConfig config = new JerseyConfig();
        ServletContainer container = new ServletContainer(config);
        ServletHolder servletHolder = new ServletHolder(container);
        context.addServlet(servletHolder, "/api/*");

        URL webAppDir = Main.class.getClassLoader().getResource("META-INF/resources");
        context.setResourceBase(webAppDir.toURI().toString());
        context.addServlet(DefaultServlet.class, "/*");

        server.setHandler(context);

        try {
            server.start();
            System.out.println("Jetty server started on port " + DEFAULT_PORT);
            server.join();
        } catch (Exception e) {
            System.err.println("Error starting Jetty server: " + e.getMessage());
            server.destroy();
            System.exit(1);
        }
    }
}
