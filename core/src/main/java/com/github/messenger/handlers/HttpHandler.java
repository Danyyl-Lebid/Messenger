package com.github.messenger.handlers;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.controllers.registration.IRegistrationController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHandler extends HttpServlet {

    private final IAuthorizationController authorizationController;

    private final IRegistrationController registrationController;

    private final ExceptionHandler exceptionHandler;

    private static final String NICKNAME = "Nickname";

    private static final String TOKEN = "Token";

    private static final Map<String, IHttpHandlerStrategy> postStrategyMap = new HashMap<>();

    public HttpHandler(
            IAuthorizationController authorizationController,
            IRegistrationController registrationController,
            ExceptionHandler exceptionHandler
    ) {
        this.authorizationController = authorizationController;
        this.registrationController = registrationController;
        this.exceptionHandler = exceptionHandler;
        this.initializePostStrategyMap();
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
        if(!postStrategyMap.containsKey(url)){
            resp.setStatus(404);
            return;
        }
        postStrategyMap.get(url).doHandle(req, resp);
    }

    public void initializePostStrategyMap(){
        postStrategyMap.put("/messenger/auth", authorizationStrategy);
        postStrategyMap.put("/messenger/reg", registrationStrategy);
    }

    private final IHttpHandlerStrategy authorizationStrategy = new IHttpHandlerStrategy() {
        @Override
        public void doHandle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            ServletOutputStream out = resp.getOutputStream();
            String body = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> result = authorizationController.authorize(body);
            resp.setHeader(NICKNAME, result.get(NICKNAME));
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            out.write(result.get(TOKEN).getBytes());
        }
    };

    private final IHttpHandlerStrategy registrationStrategy = new IHttpHandlerStrategy() {
        @Override
        public void doHandle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String body = req.getReader().lines().collect(Collectors.joining());
            registrationController.register(body);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    };

}
