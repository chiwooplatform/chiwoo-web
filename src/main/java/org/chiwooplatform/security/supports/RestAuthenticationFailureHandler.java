package org.chiwooplatform.security.supports;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class RestAuthenticationFailureHandler
    implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException exception )
        throws IOException, ServletException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        response.sendError( status.value(), status.getReasonPhrase() );
    }
}