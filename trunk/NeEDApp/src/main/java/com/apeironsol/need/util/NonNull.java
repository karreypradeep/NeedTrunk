package com.apeironsol.need.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Non null annotation. Can be used to check if method or constructor
 * parameter is not null.
 * 
 * @author Pradeep
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NonNull {
}
