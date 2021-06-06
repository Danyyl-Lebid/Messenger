package com.github.messenger.handlers;

import com.github.messenger.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.InternalError;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExceptionHandler {

    private final Map<Class<? extends Throwable>, Integer> exceptionMap = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    public ExceptionHandler() {
        exceptionMap.put(BadRequest.class, HttpServletResponse.SC_BAD_REQUEST);
        exceptionMap.put(ExpiredToken.class, HttpServletResponse.SC_UNAUTHORIZED);
        exceptionMap.put(Forbidden.class, HttpServletResponse.SC_FORBIDDEN);
        exceptionMap.put(Conflict.class, HttpServletResponse.SC_CONFLICT);
        exceptionMap.put(UpdateError.class, HttpServletResponse.SC_CONFLICT);
        exceptionMap.put(InternalError.class, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        exceptionMap.put(IOException.class, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    public void handle(HttpServletResponse resp, Throwable exception){
        log.info("Exception: {}, Message: {}", exception.getClass().getSimpleName(), exception.getMessage());
        resp.setStatus(exceptionMap.get(exception.getClass()));
        if(Objects.nonNull(exception.getMessage())) {
            try (ServletOutputStream out = resp.getOutputStream()) {
                out.write(exception.getMessage().getBytes());
            } catch (IOException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

}
