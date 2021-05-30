package com.github.di.factory;

import com.github.di.annotations.CustomComponent;
import com.github.di.factory.fortest.SecondBeanService;
import com.github.di.factory.libs.HibernateClass;

@CustomComponent
public class FirstBean {

    private final SecondBeanService secondBeanService;

    private final HibernateClass hibernateClass;

    public FirstBean(SecondBeanService secondBeanService, HibernateClass hibernateClass) {
        this.secondBeanService = secondBeanService;
        this.hibernateClass = hibernateClass;
    }

    public SecondBeanService getSecondBeanService() {
        return secondBeanService;
    }

    public HibernateClass getUnconfigurationClass() {
        return hibernateClass;
    }
}
