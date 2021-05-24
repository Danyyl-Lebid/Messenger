package com.github.di.factory.config;

import com.github.di.annotations.CustomBean;
import com.github.di.annotations.CustomConfiguration;
import com.github.di.annotations.InjectValue;
import com.github.di.factory.fortest.MyCustomBeanRepository;
import com.github.di.factory.libs.HibernateClass;

@CustomConfiguration
public class AppConfig {

    private final MyCustomBeanRepository myCustomBeanRepository;


    public AppConfig(MyCustomBeanRepository myCustomBeanRepository) {
        this.myCustomBeanRepository = myCustomBeanRepository;
    }

    @InjectValue(name = "new.injected.value")
    private String myCustomValue;

    @CustomBean
    public HibernateClass unconfigurationClass(){
        System.out.println(myCustomBeanRepository);
        return new HibernateClass(myCustomValue);
    }

}
