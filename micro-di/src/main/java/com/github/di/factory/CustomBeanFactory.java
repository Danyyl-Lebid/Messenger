package com.github.di.factory;

import com.github.di.annotations.CustomComponent;
import com.github.di.annotations.CustomConfiguration;
import com.github.di.annotations.CustomRepository;
import com.github.di.annotations.CustomService;
import com.github.di.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Rules
 * - inject only by final fields;
 * - inject only by constructor;
 * - add config value by annotation @InjectValue
 * - Config classes with custom bean marked as CustomBean methods always bean return value
 */

public class CustomBeanFactory {

    private static final CustomBeanFactory instance = new CustomBeanFactory();

    private Map<String, Object> properties = new HashMap<>();

    private Map<Class<?>, Object> container = new HashMap<>();

    private Map<Class<?>, List<Class<?>>> adjacency = new HashMap<>();

    private List<Class<?>> interfaces = new ArrayList<>();

    public static CustomBeanFactory factory() {
        return instance;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void registration(Class<?> mainClz) {
        List<Class<?>> classes = ReflectionUtils.findAllClasses(mainClz.getPackageName());
        this.adjacency = classes.stream().filter(clz ->
                clz.isAnnotationPresent(CustomService.class) ||
                        clz.isAnnotationPresent(CustomComponent.class) ||
                        clz.isAnnotationPresent(CustomRepository.class) ||
                        clz.isAnnotationPresent(CustomConfiguration.class)
        ).collect(Collectors.toMap(Function.identity(), v -> new ArrayList()));
        List<Class<?>> interfaces = classes.stream()
                .filter(Class::isInterface)
                .collect(Collectors.toList());

        for (Class<?> clz : interfaces) {
            for (Class<?> clz1 : classes) {
                Class<?>[] inter = clz1.getInterfaces();
                for (Class<?> i : inter) {
                    if (i.equals(clz)) {
                        this.interfaces.add(clz1);
                    }
                }
            }
        }
    }

    public void dependency() {
        Set<Class<?>> keys = this.adjacency.keySet();
        for (Class<?> source : keys) {
            List<Class<?>> sourceClasses = ReflectionUtils.findFinalFields(source, this.interfaces);
            if (this.adjacency.containsKey(source)) {
                this.adjacency.put(source, sourceClasses);
            }
            for (Class<?> destination : sourceClasses) {
                List<Class<?>> destinationClasses = ReflectionUtils.findFinalFields(destination, this.interfaces);
                if (this.adjacency.containsKey(destination)) {
                    this.adjacency.put(destination, destinationClasses);
                }
            }
        }
        //System.out.println(this.adjacency);
    }

    public void wiredObjects() {
        dependency();
        //find all unconnected
        List<Class<?>> unconnected = findAllUnconnected();
        for (Class<?> clz : unconnected) {
            Object obj = unconnectedObj(clz);
            this.container.put(clz, obj);
            if (clz.isAnnotationPresent(CustomConfiguration.class)) {
                createBeanMethod(clz, obj);
            }
        }

        //use unconnected and init classes
        //all classes witch not wired
        for (Class<?> clz : this.adjacency.keySet()) {
            List<Class<?>> classes = this.adjacency.get(clz);
            if (unconnected.containsAll(classes)) {
                Object obj = connectedObj(clz, classes);
                this.container.put(clz, obj);
                if (clz.isAnnotationPresent(CustomConfiguration.class)) {
                    createBeanMethod(clz, obj);
                }
            }
        }

        for (Class<?> clz : this.adjacency.keySet()) {
            if (!this.container.containsKey(clz)) {
                Object obj = connectedObj(clz, this.adjacency.get(clz));
                this.container.put(clz, obj);
                if (clz.isAnnotationPresent(CustomConfiguration.class)) {
                    createBeanMethod(clz, obj);
                }
            }
        }
    }

    private List<Class<?>> findAllUnconnected() {
        return this.adjacency.keySet().stream()
                .filter(clz -> this.adjacency.get(clz).isEmpty())
                .collect(Collectors.toList());
    }

    private Object unconnectedObj(Class<?> clz) {
        Object obj = ReflectionUtils.newInstanceWithoutParams(clz);
        if (ReflectionUtils.isHasProps(clz)) {
            ReflectionUtils.injectProps(obj, clz, this.properties);
        }
        return obj;
    }

    private Object connectedObj(Class<?> clz, List<Class<?>> classes) {
        List<Object> objects = classes.stream().map(c -> this.container.get(c))
                .collect(Collectors.toList());
        Object obj = ReflectionUtils.newInstanceWithParams(clz, objects);
        if (ReflectionUtils.isHasProps(clz)) {
            ReflectionUtils.injectProps(obj, clz, this.properties);
        }
        return obj;
    }

    private void createBeanMethod(Class<?> clz, Object obj) {
        List<Method> methods = ReflectionUtils.findDeclaredBeanMethods(clz);
        for (Method m : methods) {
            this.container.put(m.getReturnType(), ReflectionUtils.invokeBeanMethod(m, obj));
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T instance(Class<?> clz) {
        return (T) this.container.get(clz);
    }

}
