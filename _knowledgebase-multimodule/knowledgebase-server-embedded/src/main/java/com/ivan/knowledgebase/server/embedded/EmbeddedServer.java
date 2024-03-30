package com.ivan.knowledgebase.server.embedded;

import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class EmbeddedServer {
    private static final int DEFAULT_PORT = 8080;

    public static void run(String[] args, String resourcesPath) throws Exception {
        Server server = new Server(DEFAULT_PORT);

        ServletContextHandler context = createServletContextHandler(resourcesPath);
        ResourceConfig config = configureJerseyResources();
        createJerseyServletContainer(context, config);

        URL webAppDir = EmbeddedServer.class.getClassLoader().getResource(resourcesPath);
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

    private static void createJerseyServletContainer(ServletContextHandler context, ResourceConfig config) {
        ServletContainer container = new ServletContainer(config);
        ServletHolder servletHolder = new ServletHolder(container);
        context.addServlet(servletHolder, "/api/*");
    }

    private static ResourceConfig configureJerseyResources() {
        ResourceConfig config = new JerseyConfig();
        return config;
    }

    private static ServletContextHandler createServletContextHandler(String resourcesPath) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        return context;
    }

}
