package com.github.messenger.controllers.authorization.impl;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.dto.user.UserAuthDto;
import com.github.messenger.entity.User;
import com.github.messenger.exceptions.BadRequest;
import com.github.messenger.payload.PrivateToken;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.utils.JsonHelper;
import com.github.messenger.utils.PrivateTokenProvider;
import com.github.messenger.utils.RegexUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationController implements IAuthorizationController {

    private final IUserService userService;

    public AuthorizationController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Map<String, String> authorize(String json) {
        UserAuthDto dto = JsonHelper.fromJson(json, UserAuthDto.class)
                .orElseThrow(BadRequest::new);
        User user = findUser(dto);
        PrivateToken privateToken = new PrivateToken(user.getId(), user.getLogin(), 30);
        String encodedToken = PrivateTokenProvider.encode(privateToken);
        Map<String, String> result = new HashMap<>();
        result.put("Nickname", user.getNickname());
        result.put("Token", encodedToken);
        return result;
    }

    private User findUser(UserAuthDto dto) {
        if (RegexUtils.isValidEmail(dto.getLogin())) {
            return userService.findByEmail(dto.getLogin());
        } else {
            return userService.findByLogin(dto.getLogin());
        }
    }


}
