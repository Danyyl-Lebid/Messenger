package com.github.messenger.controllers.registration.impl;

import com.github.messenger.controllers.registration.IRegistrationController;
import com.github.messenger.dto.UserRegDto;
import com.github.messenger.exceptions.BadRequest;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.utils.JsonHelper;

import java.util.Objects;

public class RegistrationController implements IRegistrationController {

    private final IUserService userService;

    public RegistrationController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void register(String json) {
        UserRegDto dto = JsonHelper.fromJson(json, UserRegDto.class).orElseThrow(BadRequest::new);
        if (!Objects.equals(dto.getPassword(), dto.getPasswordConfirm())){
            throw new BadRequest();
        }
        this.userService.insert(dto.toUser());
    }
}
