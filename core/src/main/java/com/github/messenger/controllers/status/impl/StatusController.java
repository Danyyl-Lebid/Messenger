package com.github.messenger.controllers.status.impl;

import com.github.messenger.controllers.status.IStatusController;
import com.github.messenger.entity.User;
import com.github.messenger.payload.Status;
import com.github.messenger.service.user.IUserService;

public class StatusController implements IStatusController {

    private final IUserService userService;

    public StatusController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void setOnline(Long userId) {
        User user = userService.findById(userId);
        user.setStatus(Status.ONLINE);
    }

    @Override
    public void setOffline(Long userId) {
        User user = userService.findById(userId);
        user.setStatus(Status.OFFLINE);
    }
}
