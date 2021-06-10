package com.github.messenger.handlers.http;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.controllers.registration.IRegistrationController;

import java.util.Objects;

public class HttpHandlerStrategyContext {

    private final IAuthorizationController authorizationController;

    private final IRegistrationController registrationController;

    public HttpHandlerStrategyContext(IAuthorizationController authorizationController, IRegistrationController registrationController) {
        this.authorizationController = authorizationController;
        this.registrationController = registrationController;
    }

    public IAuthorizationController getAuthorizationController() {
        return authorizationController;
    }

    public IRegistrationController getRegistrationController() {
        return registrationController;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHandlerStrategyContext that = (HttpHandlerStrategyContext) o;
        return Objects.equals(authorizationController, that.authorizationController) && Objects.equals(registrationController, that.registrationController);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorizationController, registrationController);
    }
}
