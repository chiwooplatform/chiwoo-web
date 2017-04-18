package org.chiwooplatform.web.mvc.supports;

import java.util.Iterator;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.chiwooplatform.context.model.ParameterMap;

public class ParameterMapMethodArgumentResolver
    implements HandlerMethodArgumentResolver {

    /**
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
     */
    @Override
    public boolean supportsParameter( MethodParameter parameter ) {
        System.out.printf( "\n ParameterMap.class.isAssignableFrom( parameter.getParameterType() ): %s",
                           ParameterMap.class.isAssignableFrom( parameter.getParameterType() ) );
        return ParameterMap.class.isAssignableFrom( parameter.getParameterType() );
    }

    /**
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter,
     * org.springframework.web.method.support.ModelAndViewContainer,
     * org.springframework.web.context.request.NativeWebRequest,
     * org.springframework.web.bind.support.WebDataBinderFactory)
     */
    @Override
    public Object resolveArgument( MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory )
        throws Exception {
        Iterator<String> parameterNames = webRequest.getParameterNames();
        if ( parameterNames == null ) {
            return null;
        }
        ParameterMap param = new ParameterMap();
        for ( Iterator<String> iterator = parameterNames; iterator.hasNext(); ) {
            String key = iterator.next();
            Object value = webRequest.getParameter( key );
            param.put( key, value );
        }
        return param;
    }
}
