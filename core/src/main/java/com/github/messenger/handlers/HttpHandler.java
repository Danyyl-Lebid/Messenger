package com.github.messenger.handlers;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.controllers.registration.IRegistrationController;
import com.github.messenger.exceptions.BadRequest;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class HttpHandler extends HttpServlet {

    private final IAuthorizationController authorizationController;

    private final IRegistrationController registrationController;

    public HttpHandler(IAuthorizationController authorizationController, IRegistrationController registrationController) {
        this.authorizationController = authorizationController;
        this.registrationController = registrationController;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "*");
            resp.setHeader("Access-Control-Allow-Headers", "*");
            super.service(req, resp);
        } catch (BadRequest e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid body");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String url = req.getRequestURI();
        switch (url) {
            case "/users/auth":
                try (ServletOutputStream out = resp.getOutputStream()) {
                    String body = req.getReader().lines().collect(Collectors.joining());
                    String result = this.authorizationController.authorize(body);
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setStatus(200);
                    out.write(result.getBytes());
                } catch (IOException e) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                break;
            case "/users/reg":
                try (ServletOutputStream out = resp.getOutputStream()) {
                    String body = req.getReader().lines().collect(Collectors.joining());
                    this.registrationController.register(body);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } catch (IOException e) {
                    resp.setStatus(500);
                }
                break;
            default:
                resp.setStatus(404);
        }
    }

}
