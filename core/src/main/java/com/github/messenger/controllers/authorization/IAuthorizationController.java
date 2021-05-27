package com.github.messenger.controllers.authorization;

import java.util.Map;

public interface IAuthorizationController {

    Map<String, String> authorize(String json);

}
