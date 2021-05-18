package com.github.messenger.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateSessionManager {

    private final SessionFactory sessionFactory;

    public HibernateSessionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

}
