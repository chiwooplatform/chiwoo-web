package org.chiwooplatform.security.supports;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class RestAuthenticationSuccessHandler
    extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    protected void handle( HttpServletRequest request, HttpServletResponse response, Authentication authentication )
        throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest( request, response );
        if ( savedRequest == null ) {
            clearAuthenticationAttributes( request );
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if ( isAlwaysUseDefaultTargetUrl()
            || ( targetUrlParam != null && StringUtils.hasText( request.getParameter( targetUrlParam ) ) ) ) {
            requestCache.removeRequest( request, response );
            clearAuthenticationAttributes( request );
            return;
        }
        clearAuthenticationAttributes( request );
    }
}