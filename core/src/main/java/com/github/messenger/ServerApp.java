package com.github.messenger;

import com.github.messenger.config.ServerConfig;

import javax.servlet.ServletException;

public class ServerApp {

    public static void main(String[] args) throws ServletException {
        ServerConfig.tomcat().run();
    }

}
