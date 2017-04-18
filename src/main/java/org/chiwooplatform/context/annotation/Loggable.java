package org.chiwooplatform.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Loggable {

    LogLevel value() default LogLevel.DEBUG;

    boolean showArgs() default true;

    boolean showResult() default true;

    boolean useAbbr() default true;
}