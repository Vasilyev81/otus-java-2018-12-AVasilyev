package ru.otus.l101.dao;

		import org.hibernate.annotations.Type;

		import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface TableName {
	String tableName();
}
