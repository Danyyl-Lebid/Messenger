package com.github.messenger.utils;

import com.github.messenger.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionManager {

    private final SessionFactory sessionFactory;

    public HibernateSessionManager() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }

}
