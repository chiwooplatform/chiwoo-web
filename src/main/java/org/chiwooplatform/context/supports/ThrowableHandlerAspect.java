package org.chiwooplatform.context.supports;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.chiwooplatform.web.exception.GeneralException;
import org.chiwooplatform.web.exception.ServiceException;
import org.chiwooplatform.web.supports.WebUtils;

@Order(1)
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ThrowableHandlerAspect {

    @Pointcut("@within(org.springframework.stereotype.Controller) || @annotation(org.springframework.stereotype.Controller)")
    void controllerPointcut() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.RestController)")
    void restPointcut() {
    }

    @Around("controllerPointcut() || restPointcut()")
    public Object handleControllerException( ProceedingJoinPoint point )
        throws Throwable {
        Object result = null;
        try {
            result = point.proceed();
        } catch ( ServiceException se ) {
            throw se;
        } catch ( Exception e ) {
            throw WebUtils.generalException( e );
        }
        return result;
    }

    @Pointcut("@within(org.springframework.stereotype.Repository) || @annotation(org.springframework.stereotype.Repository)")
    void repositoryPointcut() {
    }

    @Pointcut("@within(org.apache.ibatis.annotations.Mapper) || @annotation(org.apache.ibatis.annotations.Mapper)")
    void mapperPointcut() {
    }

    @Around("mapperPointcut() || repositoryPointcut()")
    public Object handleServiceException( ProceedingJoinPoint point )
        throws Throwable {
        try {
            Object result = point.proceed();
            return result;
        } catch ( Exception e ) {
            if ( e instanceof ServiceException || e instanceof GeneralException ) {
                throw e;
            } else {
                throw new ServiceException( e );
            }
        }
    }
}