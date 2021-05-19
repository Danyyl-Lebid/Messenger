package com.github.messenger.controllers.authorization.impl;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.dto.UserAuthDto;
import com.github.messenger.entity.User;
import com.github.messenger.exceptions.BadRequest;
import com.github.messenger.payload.PrivateToken;
import com.github.messenger.payload.PublicToken;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.utils.JsonHelper;
import com.github.messenger.utils.PrivateTokenProvider;
import com.github.messenger.utils.PublicTokenProvider;
import com.github.messenger.utils.RegexUtils;

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
        PrivateToken privateToken = new PrivateToken(user.getLogin(), 30);
        PublicToken publicToken = new PublicToken(user.getRole(), user.getNickname());
        return PublicTokenProvider.encode(publicToken) + "." + PrivateTokenProvider.encode(privateToken);
    }

    private User findUser(UserAuthDto dto){
        if (RegexUtils.isValidEmail(dto.getLogin())){
            return userService.findByEmail(dto.getLogin());
        } else {
            return userService.findByLogin(dto.getLogin());
        }
    }


}
