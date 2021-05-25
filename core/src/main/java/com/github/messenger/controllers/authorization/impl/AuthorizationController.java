package com.github.messenger.controllers.authorization.impl;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.dto.user.UserAuthDto;
import com.github.messenger.entity.User;
import com.github.messenger.exceptions.BadRequest;
import com.github.messenger.payload.PrivateToken;
import com.github.messenger.payload.PublicToken;
import com.github.messenger.payload.Status;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.utils.*;

public class AuthorizationController implements IAuthorizationController {

    private final IUserService userService;

    public AuthorizationController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String authorize(String json) {
        UserAuthDto dto = JsonHelper.fromJson(json, UserAuthDto.class)
                .orElseThrow(BadRequest::new);
        User user = findUser(dto);
        PrivateToken privateToken = new PrivateToken(user.getId(), user.getLogin(), 30);
        PublicToken publicToken = new PublicToken(user.getRole(), user.getNickname());
        String result = PublicTokenProvider.encode(publicToken) + "." + PrivateTokenProvider.encode(privateToken);
        user.setStatus(Status.ONLINE);
        user.setLastOnline(System.currentTimeMillis());
        userService.update(user);
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
