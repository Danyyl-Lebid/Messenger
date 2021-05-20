package com.github.messenger.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HibernateSessionManager {

    private final SessionFactory sessionFactory;

    public HibernateSessionManager() {
        Configuration configuration = new Configuration();
        try {
            for (Class<?> cls : getEntityClassesFromPackage("com.github.messenger.entity")) {
                configuration.addAnnotatedClass(cls);
            }
        } catch (ClassNotFoundException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }

    public static List<Class<?>> getEntityClassesFromPackage(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        List<String> classNames = getClassNamesFromPackage(packageName);
        List<Class<?>> classes = new ArrayList<>();
        for (String className : classNames) {
            Class<?> cls = Class.forName(packageName + "." + className);
            Annotation[] annotations = cls.getAnnotations();

            for (Annotation annotation : annotations) {
                System.out.println(cls.getCanonicalName() + ": " + annotation.toString());
                if (annotation instanceof javax.persistence.Entity) {
                    classes.add(cls);
                }
            }
        }

        return classes;
    }

    public static ArrayList<String> getClassNamesFromPackage(String packageName) throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ArrayList<String> names = new ArrayList<>();

        packageName = packageName.replace(".", "/");
        URL packageURL = classLoader.getResource(packageName);

        assert packageURL != null;
        URI uri = new URI(packageURL.toString());
        File folder = new File(uri.getPath());
        File[] files = folder.listFiles();
        assert files != null;
        for (File file: files) {
            String name = file.getName();
            name = name.substring(0, name.lastIndexOf('.'));
            names.add(name);
        }

        return names;
    }
}
