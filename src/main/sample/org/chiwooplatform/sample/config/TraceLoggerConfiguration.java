package org.chiwooplatform.sample.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import org.aspectj.lang.annotation.Pointcut;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TraceLoggerConfiguration {

    @Pointcut("execution(public * (@org.springframework.web.bind.annotation.RestController org.chiwooplatform.sample..*).*(..))")
    public void restAnnotation() {
    }

    @Pointcut("execution(public * (@org.springframework.stereotype.Service org.chiwooplatform.sample..*).*(..))")
    public void serviceAnnotation() {
    }

    @Pointcut("execution(public * (@org.springframework.stereotype.Repository org.chiwooplatform.sample..*).*(..))")
    public void repositoryAnnotation() {
    }
    //    @Pointcut("execution(public * org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    //    public void jpaRepository() {}
    //    
    //    @Pointcut("execution(public * org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    //    public void mapperRepository() {}

    // @Pointcut("serviceAnnotation() || repositoryAnnotation() || jpaRepository()")
    @Pointcut("restAnnotation() || serviceAnnotation() || repositoryAnnotation()")
    public void performanceMonitor() {
    }

    @Profile({ "defaultLocal" })
    @Bean
    public CustomizableTraceInterceptor customizableTraceInterceptor() {
        CustomizableTraceInterceptor traceInterceptor = new CustomizableTraceInterceptor();
        traceInterceptor.setUseDynamicLogger( true );
        traceInterceptor.setEnterMessage( "Entering $[methodName]($[arguments])" );
        traceInterceptor.setExitMessage( "Leaving  $[methodName](), returned $[returnValue]" );
        // traceInterceptor.setLoggerName( "TraceLogger" );
        traceInterceptor.setHideProxyClassNames( true );
        return traceInterceptor;
    }

    @Profile({ "JPA" })
    @Bean
    public Advisor jpaRepositoryAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression( "execution(public * org.springframework.data.jpa.repository.JpaRepository+.*(..))" );
        return new DefaultPointcutAdvisor( pointcut, customizableTraceInterceptor() );
    }

    @Profile({ "defaultLocal" })
    @Bean
    public Advisor restControllerAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression( "org.chiwooplatform.sample.config.TraceLoggerConfiguration.performanceMonitor()" );
        return new DefaultPointcutAdvisor( pointcut, customizableTraceInterceptor() );
    }
}
