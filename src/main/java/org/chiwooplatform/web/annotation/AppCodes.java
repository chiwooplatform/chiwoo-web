package org.chiwooplatform.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * DBMS 모델 속성 중 공통 코드인 경우, 대분류 코드를 정의 한다.
 * </p>
 * 
 * <pre>
 * <b>USAGE:</b>
 * <code>
public class User
    implements Serializable
{

    &#64;AppCodes(parent = 5000, description = "user_cd 유형 코드")
    private Integer user_cd_id;
    
}
 * </code>
 * </pre>
 * 
 * @author aider
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppCodes
{
    int parent() default -1;

    String description() default "";
}