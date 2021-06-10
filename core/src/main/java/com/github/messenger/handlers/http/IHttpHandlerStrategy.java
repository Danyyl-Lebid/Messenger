package com.github.messenger.handlers.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IHttpHandlerStrategy {

    void doHandle(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
