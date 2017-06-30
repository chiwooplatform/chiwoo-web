package org.chiwooplatform.security.web.supports;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

public class NotRequestMatcher
    implements RequestMatcher {

    final RequestMatcher delegate;

    public NotRequestMatcher( RequestMatcher matcher ) {
        super();
        this.delegate = matcher;
    }

    @Override
    public boolean matches( HttpServletRequest request ) {
        return !delegate.matches( request );
    }
}
