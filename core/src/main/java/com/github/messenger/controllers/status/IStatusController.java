package com.github.messenger.controllers.status;

public interface IStatusController {

    void setOnline(Long userId);

    void setOffline(Long userId);

}
