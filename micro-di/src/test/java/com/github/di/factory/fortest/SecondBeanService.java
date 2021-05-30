package com.github.di.factory.fortest;

import com.github.di.annotations.CustomService;

@CustomService
public class SecondBeanService {

    private final MyCustomBeanRepository myCustomBeanRepository;

    public SecondBeanService(MyCustomBeanRepository myCustomBeanRepository) {
        this.myCustomBeanRepository = myCustomBeanRepository;
    }

    //for test
    public String getHello(){
        return "Hello world";
    }

    public MyCustomBeanRepository getMyCustomBeanRepository() {
        return myCustomBeanRepository;
    }
}
