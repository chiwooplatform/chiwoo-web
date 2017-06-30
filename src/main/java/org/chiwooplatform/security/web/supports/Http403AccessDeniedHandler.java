package org.chiwooplatform.security.web.supports;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http403AccessDeniedHandler
    implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger( Http403AccessDeniedHandler.class );

    @Override
    public void handle( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                        AccessDeniedException e )
        throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth != null ) {
            logger.info( "User '" + auth.getName() + "' attempted to access the protected URL: "
                + httpServletRequest.getRequestURI() );
        }
        httpServletResponse.sendRedirect( httpServletRequest.getContextPath() + "/403" );
    }
}
