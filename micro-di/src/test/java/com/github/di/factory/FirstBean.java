package com.github.di.factory;

import com.github.di.annotations.CustomComponent;
import com.github.di.factory.fortest.IMyCustomInterface;
import com.github.di.factory.fortest.SecondBeanService;
import com.github.di.factory.libs.HibernateClass;

@CustomComponent
public class FirstBean {

    private final SecondBeanService secondBeanService;

    private final HibernateClass hibernateClass;

    private final IMyCustomInterface firstRelease;

    public FirstBean(SecondBeanService secondBeanService, HibernateClass hibernateClass, IMyCustomInterface firstRelease) {
        this.secondBeanService = secondBeanService;
        this.hibernateClass = hibernateClass;
        this.firstRelease = firstRelease;
    }

    public SecondBeanService getSecondBeanService() {
        return secondBeanService;
    }

    public HibernateClass getUnconfigurationClass() {
        return hibernateClass;
    }

    public IMyCustomInterface getfIrstRelease() {
        return firstRelease;
    }
}
