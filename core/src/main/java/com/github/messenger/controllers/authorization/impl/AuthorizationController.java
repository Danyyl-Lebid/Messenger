package com.github.messenger.controllers.authorization.impl;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.dto.user.UserAuthDto;
import com.github.messenger.entity.User;
import com.github.messenger.exceptions.BadRequest;
import com.github.messenger.exceptions.Forbidden;
import com.github.messenger.payload.Token;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.utils.JsonHelper;
import com.github.messenger.utils.TokenProvider;
import com.github.messenger.utils.RegexUtils;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthorizationController implements IAuthorizationController {

    private final IUserService userService;

    public AuthorizationController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Map<String, String> authorize(String json) {
        try {

            UserAuthDto dto = JsonHelper.fromJson(json, UserAuthDto.class)
                    .orElseThrow(BadRequest::new);
            User user = findUser(dto);
            if (!Objects.equals(user.getPassword(), dto.getPassword())) {
                throw new Forbidden("Wrong password");
            }
            Token token = new Token(user.getId(), user.getLogin(), 30);
            String encodedToken = TokenProvider.encode(token);
            Map<String, String> result = new HashMap<>();
            result.put("Nickname", user.getNickname());
            result.put("Token", encodedToken);
            return result;
        } catch (NoResultException e){
            throw new Forbidden("Wrong login");
        }
    }

    private User findUser(UserAuthDto dto) {
        if (RegexUtils.isValidEmail(dto.getLogin())) {
            return userService.findByEmail(dto.getLogin());
        } else {
            return userService.findByLogin(dto.getLogin());
        }
    }

}
