package com.github.messenger.handlers;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.controllers.registration.IRegistrationController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHandler extends HttpServlet {

    private final IAuthorizationController authorizationController;

    private final IRegistrationController registrationController;

    private final ExceptionHandler exceptionHandler;

    private static final String NICKNAME = "Nickname";

    private static final String TOKEN = "Token";

    public HttpHandler(IAuthorizationController authorizationController, IRegistrationController registrationController, ExceptionHandler exceptionHandler) {
        this.authorizationController = authorizationController;
        this.registrationController = registrationController;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "*");
            resp.setHeader("Access-Control-Allow-Headers", "*");
            resp.setHeader("Access-Control-Expose-Headers", "*");
            super.service(req, resp);
        } catch (Throwable e) {
            exceptionHandler.handle(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getRequestURI();
        String body;
        switch (url) {
            case "/users/auth":
                ServletOutputStream out = resp.getOutputStream();
                body = req.getReader().lines().collect(Collectors.joining());
                Map<String, String> result = this.authorizationController.authorize(body);
                resp.setHeader(NICKNAME, result.get(NICKNAME));
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                out.write(result.get(TOKEN).getBytes());
                break;
            case "/users/reg":
                body = req.getReader().lines().collect(Collectors.joining());
                this.registrationController.register(body);
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            default:
                resp.setStatus(404);
        }
    }

}
