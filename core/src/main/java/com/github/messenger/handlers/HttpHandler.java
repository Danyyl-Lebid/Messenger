package com.github.messenger.handlers;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.controllers.chat.IChatController;
import com.github.messenger.controllers.registration.IRegistrationController;
import com.github.messenger.exceptions.BadRequest;

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

    private final IChatController chatController;

    private final ExceptionHandler exceptionHandler;

    private static final String NICKNAME = "Nickname";

    private static final String TOKEN = "Token";

    public HttpHandler(
            IAuthorizationController authorizationController,
            IRegistrationController registrationController,
            IChatController chatController,
            ExceptionHandler exceptionHandler
    ) {
        this.authorizationController = authorizationController;
        this.registrationController = registrationController;
        this.chatController = chatController;
        this.exceptionHandler = exceptionHandler;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getRequestURI();
        String body;
        switch (url) {
            case "/messenger/nicknames":
                ServletOutputStream out = resp.getOutputStream();
                out.write(this.chatController.getAllUsers().getBytes());
                break;
            default:
                resp.setStatus(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getRequestURI();
        String body;
        String respBody;
        ServletOutputStream out = resp.getOutputStream();
        switch (url) {
            case "/messenger/auth":
                body = req.getReader().lines().collect(Collectors.joining());
                Map<String, String> result = this.authorizationController.authorize(body);
                resp.setHeader(NICKNAME, result.get(NICKNAME));
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                out.write(result.get(TOKEN).getBytes());
                break;
            case "/messenger/reg":
                body = req.getReader().lines().collect(Collectors.joining());
                this.registrationController.register(body);
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            case "/messenger/create-chat":
                body = req.getReader().lines().collect(Collectors.joining());
                this.chatController.createChat(body);
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            case "/messenger/participants":
                body = req.getReader().lines().collect(Collectors.joining());
                respBody = this.chatController.getParticipants(body);
                resp.setStatus(HttpServletResponse.SC_OK);
                out.write(respBody.getBytes());
            default:
                resp.setStatus(404);
        }
    }

}
