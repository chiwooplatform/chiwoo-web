package org.chiwooplatform.context.supports;

import java.lang.reflect.Method;

import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.chiwooplatform.context.annotation.Loggable;

@Order(100)
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggingAspect {

    // org.chiwooplatform.context.supports.LoggingAspect.loggablePointcut()
    @Pointcut("@within(org.chiwooplatform.context.annotation.Loggable) || @annotation(org.chiwooplatform.context.annotation.Loggable)")
    public void loggablePointcut() {
    }

    @AfterThrowing(pointcut = "loggablePointcut()", throwing = "ex")
    public void processLogException( Throwable ex ) {
        LoggerWriter.writeToLog( LogLevel.ERROR, ex );
    }

    @Around("loggablePointcut()")
    public Object processLog( ProceedingJoinPoint point )
        throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Loggable loggableMethod = method.getAnnotation( Loggable.class );
        final Class<?> tagetClazz = point.getTarget().getClass();
        Loggable loggableClass = tagetClazz.getAnnotation( Loggable.class );
        //get current log level
        LogLevel logLevel = loggableMethod != null ? loggableMethod.value() : loggableClass.value();
        //show arguements
        boolean showParam = loggableMethod != null ? loggableMethod.showArgs() : loggableClass.showArgs();
        boolean useabbr = loggableMethod != null ? loggableMethod.useAbbr() : loggableClass.useAbbr();
        StringBuilder builder = new StringBuilder();
        if ( useabbr ) {
            builder.append( tagetClazz.getSimpleName() ).append( "." ).append( method.getName() ).append( "(" );
        } else {
            builder.append( tagetClazz.getName() ).append( "." ).append( method.getName() ).append( "(" );
        }
        StringBuilder entering = new StringBuilder();
        entering.append( "Entering " ).append( builder );
        if ( showParam ) {
            Object[] args = point.getArgs();
            if ( args != null && args.length > 0 ) {
                for ( int i = 0; i < point.getArgs().length; i++ ) {
                    entering.append( point.getArgs()[i] );
                    if ( i < args.length - 1 )
                        entering.append( ", " );
                }
            }
        }
        entering.append( ")" );
        LoggerWriter.writeToLog( logLevel, entering.toString() );
        StringBuilder leaving = new StringBuilder();
        leaving.append( "Leaving  " ).append( builder );
        leaving.append( ")" );
        long startTime = System.currentTimeMillis();
        //start method execution
        try {
            Object result = point.proceed();
            //show results
            if ( result != null ) {
                boolean showResults = loggableMethod != null ? loggableMethod.showResult() : loggableClass.showResult();
                if ( showResults ) {
                    leaving.append( ", " );
                    if ( result.getClass().isAssignableFrom( ResponseEntity.class ) ) {
                        ResponseEntity<?> res = (ResponseEntity<?>) result;
                        leaving.append( "returned <" ).append( res.getStatusCode() );
                        leaving.append( ", headers: " ).append( res.getHeaders() );
                        leaving.append( ", payload: " ).append( res.getBody() );
                        leaving.append( ">" );
                    } else {
                        leaving.append( "returned " + result );
                    }
                }
            }
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            leaving.append( ", elapsed <" ).append( ( endTime - startTime ) ).append( " millis>." );
            //show after
            LoggerWriter.writeToLog( logLevel, leaving.toString() );
        }
    }
}