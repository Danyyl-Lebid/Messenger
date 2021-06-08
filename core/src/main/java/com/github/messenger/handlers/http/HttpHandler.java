package com.github.messenger.handlers.http;

import com.github.messenger.handlers.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpHandler extends HttpServlet {

    private final ExceptionHandler exceptionHandler;

    private final HttpHandlerStrategyMap strategyMap;

    public HttpHandler(
            HttpHandlerStrategyMap strategyMap,
            ExceptionHandler exceptionHandler
    ) {
        this.exceptionHandler = exceptionHandler;
        this.strategyMap = strategyMap;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (Throwable e) {
            exceptionHandler.handle(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getRequestURI();
        if (!strategyMap.getMap().containsKey(url)) {
            resp.setStatus(404);
            return;
        }
        strategyMap.getMap().get(url).doHandle(req, resp);
    }

}
