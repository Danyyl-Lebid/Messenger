package com.github.messenger.utils;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class ServerRunner {

    private static final Logger log = LoggerFactory.getLogger(ServerRunner.class);

    private final Tomcat tomcat;

    private Context ctx;

    private final List<Consumer<Context>> listeners;

    public ServerRunner(Tomcat tomcat, Context ctx, List<Consumer<Context>> listeners) {
        this.tomcat = tomcat;
        this.ctx = ctx;
        this.listeners = listeners;
    }

    public void run(){
        try {
            this.tomcat.start();
            this.listeners.forEach(lst -> lst.accept(this.ctx));
            this.tomcat.getServer().await();
        } catch (LifecycleException e){
            log.warn("Enter: {}", e.getMessage());
        }
    }

}
