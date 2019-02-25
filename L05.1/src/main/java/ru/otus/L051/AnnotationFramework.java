package ru.otus.L051;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.jetbrains.annotations.*;

class AnnotationFramework {

    private static List<Class<? extends Annotation>> declaredAnnotations = new ArrayList<>();
    private static Map<String, Method> annotatedMethods = new HashMap<>();
    private static List<Method> methodsForTest = new LinkedList<>();
    private static Class clazz;

    public static void runTests(Class<? extends Object> testClass) {
        declaredAnnotations = Arrays.asList(BeforeAll.class, BeforeEach.class, AfterEach.class, AfterAll.class);
        clazz = testClass;
        if (methodsIsAnnotated(clazz)) {
            getAnnotatedMethods();
            runTestSequence();
        }
    }

    private static boolean methodsIsAnnotated(@NotNull Class<?> clazz) {
        boolean result = false;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (methodIsAnnotated(method)) result = true;
        }
        return result;
    }

    private static boolean methodIsAnnotated(@NotNull Method method) { //TODO: Problem is somewhere in this method
        boolean result = false;
        Annotation[] methodAnnotations = method.getAnnotations();
        for (Annotation methodAnnotation : methodAnnotations) {
            for (Class<? extends Annotation> annotation : declaredAnnotations) {
                if ((methodAnnotation.annotationType().getSimpleName())
                        .equals(annotation.getSimpleName()) | result) {
                    result = true;
                }
            }
        }
        return result;
    }

    private static void getAnnotatedMethods() {
        for (Class<? extends Annotation>annotation : declaredAnnotations) {
            annotatedMethods.put(annotation.getSimpleName(), null);
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length > 0) {
                if ((annotations[0].annotationType().getSimpleName())
                        .equals(Test.class.getSimpleName())) {
                    methodsForTest.add(method);
                } else {
                    annotatedMethods.put(annotations[0].annotationType().getSimpleName()
                            , method);
                }
            }
        }
    }

    private static void runTestSequence() {
        try {
            annotatedMethods.get(BeforeAll.class.getSimpleName()).invoke(clazz);
            runTestMethods();
            annotatedMethods.get(AfterAll.class.getSimpleName()).invoke(clazz);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void runTestMethods() {
        for (Method method : methodsForTest) {
            Object object = ReflectionHelper.instantiate(clazz);
            try {
                annotatedMethods.get(BeforeEach.class.getSimpleName()).invoke(object);
                method.invoke(object);
                annotatedMethods.get(AfterEach.class.getSimpleName()).invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

