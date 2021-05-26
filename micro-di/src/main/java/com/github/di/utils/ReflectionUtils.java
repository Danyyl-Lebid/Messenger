package com.github.di.utils;

import com.github.di.annotations.CustomBean;
import com.github.di.annotations.InjectValue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionUtils {

    public static List<Class<?>> findFinalFields(Class<?> clz) {
        List<Class<?>> result = new ArrayList<>();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                result.add(field.getType());
            }
        }
        return result;
    }

    public static List<Class<?>> findFinalFields(Class<?> clz, List<Class<?>> classes) {
        List<Class<?>> result = new ArrayList<>();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                if (field.getType().isInterface()){
                    Class<?> type = classes.stream().filter(c -> c.getSimpleName().equalsIgnoreCase(field.getName()))
                            .findFirst().orElseThrow();
                    result.add(type);
                } else {
                    result.add(field.getType());
                }
            }
        }
        return result;
    }

    public static Object newInstanceWithoutParams(Class<?> clz) {
        try {
            return clz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Can't construct instance without params");
        }
    }

    public static Object newInstanceWithParams(Class<?> clz, List<Object> instances) {
        try {
            return clz.getConstructor(constructorOfClasses(clz))
                    .newInstance(instances.toArray());
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Can't construct instance without params");
        }
    }

    private static Class<?>[] constructorOfClasses(Class<?> clz){
        return clz.getConstructors()[0].getParameterTypes();
    }

    public static boolean isHasProps(Class<?> clz) {
        return Arrays.stream(clz.getDeclaredFields()).
                anyMatch(f -> f.isAnnotationPresent(InjectValue.class));
    }

    public static void injectProps(Object obj, Class<?> clz, Map<String, Object> props) {
        Field[] fields = clz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(Boolean.TRUE);
                if (field.isAnnotationPresent(InjectValue.class)){
                    String key = field.getAnnotation(InjectValue.class).name();
                    field.set(obj, props.get(key));
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static List<Method> findDeclaredBeanMethods(Class<?> clz){
        return Arrays.stream(clz.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(CustomBean.class))
                .collect(Collectors.toList());
    }

    public static Object invokeBeanMethod(Method m, Object obj){
        try {
            return m.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Can't invoke method");
        }
    }

    public static List<Class<?>> findAllClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            List<File> dirs = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            for (File directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

}
