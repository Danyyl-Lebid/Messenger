package com.github.messenger.config;

import com.github.messenger.handlers.WebsocketHandler;
import com.github.messenger.utils.ServerRunner;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class ServerConfig {

    public static ServerRunner tomcat() throws ServletException {
        Tomcat tomcat = new Tomcat();
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        tomcat.setPort(Integer.parseInt(webPort));
        Context ctx = tomcat.addWebapp("/", new File(".").getAbsolutePath());
        tomcat.addServlet("", HttpHandlerConfig.getHttpHandler().getClass().getName(), HttpHandlerConfig.getHttpHandler());
        ctx.addServletMappingDecoded("/messenger/*", HttpHandlerConfig.getHttpHandler().getClass().getName());
        return new ServerRunner(tomcat, ctx, List.of(chatWebsocketHandler));
    }

    private static final Consumer<Context> chatWebsocketHandler = ctx -> {
        ServerContainer serverContainer =
                (ServerContainer) ctx.getServletContext().getAttribute(ServerContainer.class.getName());
        try {
            serverContainer.addEndpoint(
                    ServerEndpointConfig.Builder.create(WebsocketHandler.class, "/chat")
                            .configurator(new ServerEndpointConfig.Configurator() {
                                              @Override
                                              public <T> T getEndpointInstance(Class<T> clazz) {
                                                  return (T) WebsocketHandlerConfig.getWebsocketHandler();
                                              }
                                          }
                            ).build()
            );
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    };
}
