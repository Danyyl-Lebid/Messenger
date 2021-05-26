package com.github.di.factory;

import com.github.di.factory.fortest.MyCustomBeanRepository;
import com.github.di.factory.fortest.SecondBeanService;
import com.github.di.factory.libs.HibernateClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CustomBeanFactoryTest {

    @Test
    public void registration(){
        Map<String, Object> properties = new HashMap<>();
        properties.put("my.value", "hello world");
        properties.put("new.injected.value", "ololololo");
        CustomBeanFactory factory = CustomBeanFactory.factory();
        factory.setProperties(properties);
        factory.registration(FirstBean.class);
        //factory.dependency();
        factory.wiredObjects();
        MyCustomBeanRepository repo = factory.instance(MyCustomBeanRepository.class);
        assertEquals("hello world", repo.getMyValue());
        SecondBeanService secondBeanService = factory.instance(SecondBeanService.class);
        assertEquals("Hello world", secondBeanService.getHello());
        FirstBean firstBean = factory.instance(FirstBean.class);
        assertEquals("Hello world", firstBean.getSecondBeanService().getHello());
        assertEquals("ololololo", firstBean.getUnconfigurationClass().getStr());
        //System.out.println(repo.getMyValue());
        HibernateClass hibernateClass = factory.instance(HibernateClass.class);
        System.out.println(hibernateClass.getStr());

        assertEquals("My name is Tony", firstBean.getfIrstRelease().getName());
    }


}