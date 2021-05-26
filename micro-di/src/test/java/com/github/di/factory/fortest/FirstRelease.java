package com.github.di.factory.fortest;

import com.github.di.annotations.CustomComponent;

@CustomComponent
public class FirstRelease implements IMyCustomInterface{

    private String str = "My name is Tony";

    @Override
    public String getName() {
        return str;
    }
}
