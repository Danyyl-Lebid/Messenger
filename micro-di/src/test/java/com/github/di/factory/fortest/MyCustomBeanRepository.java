package com.github.di.factory.fortest;

import com.github.di.annotations.CustomRepository;
import com.github.di.annotations.InjectValue;

@CustomRepository
public class MyCustomBeanRepository {

    @InjectValue(name = "my.value")
    private String myValue;

    public String getMyValue() {
        return myValue;
    }
}
