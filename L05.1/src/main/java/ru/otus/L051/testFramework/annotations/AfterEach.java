package ru.otus.L051.testFramework.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited

public @interface AfterEach {
}
