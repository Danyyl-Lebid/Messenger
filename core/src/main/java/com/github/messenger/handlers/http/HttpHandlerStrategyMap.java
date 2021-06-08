package com.github.messenger.handlers.http;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHandlerStrategyMap {

    private final Map<String, IHttpHandlerStrategy> map = new HashMap<>();

    private final HttpHandlerStrategyContext context;

    private static final String NICKNAME = "Nickname";

    private static final String TOKEN = "Token";

    public HttpHandlerStrategyMap(HttpHandlerStrategyContext context) {
        this.context = context;
        this.map.put("/messenger/auth", authorizationStrategy);
        this.map.put("/messenger/reg", registrationStrategy);
    }

    private final IHttpHandlerStrategy authorizationStrategy = new IHttpHandlerStrategy() {
        @Override
        public void doHandle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            ServletOutputStream out = resp.getOutputStream();
            String body = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> result = context.getAuthorizationController().authorize(body);
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
            context.getRegistrationController().register(body);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    };

    public Map<String, IHttpHandlerStrategy> getMap() {
        return map;
    }
}
