package org.chiwooplatform.security.supports;

import java.util.ArrayList;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.security.authentication.RestAuthenticationToken;

public class RestAuthenticationFilter
    extends AbstractAuthenticationProcessingFilter {

    private OrRequestMatcher excludMatcher;

    public void setExcludUrlPatterns( String... urlPatterns ) {
        if ( urlPatterns != null ) {
            ArrayList<RequestMatcher> requestMatchers = new ArrayList<>();
            for ( String pattern : urlPatterns ) {
                requestMatchers.add( new AntPathRequestMatcher( pattern ) );
            }
            excludMatcher = new OrRequestMatcher( requestMatchers );
        }
    }

    public RestAuthenticationFilter() {
        super( "/**" );
    }

    public RestAuthenticationFilter( String filterProcessesUrl ) {
        super( filterProcessesUrl );
    }

    @Override
    protected boolean requiresAuthentication( HttpServletRequest request, HttpServletResponse response ) {
        if ( excludMatcher != null ) {
            if ( excludMatcher.matches( request ) ) {
                return false;
            }
        }
        return super.requiresAuthentication( request, response );
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response )
        throws AuthenticationException, IOException {
        logger.trace( "getAuthenticationManager(): " + getAuthenticationManager() );
        final String sessionId = request.getSession().getId();
        final String token = request.getHeader( Constants.AUTH_TOKEN );
        RestAuthenticationToken authentication = new RestAuthenticationToken( token, sessionId, null );
        if ( !StringUtils.isEmpty( token ) ) {
            // Authentication newAuthentication = getAuthenticationManager().authenticate( authentication );
            // SecurityContextHolder.getContext().setAuthentication( authentication );
            return getAuthenticationManager().authenticate( authentication );
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain, Authentication authResult )
        throws IOException, ServletException {
        super.successfulAuthentication( request, response, chain, authResult );
        chain.doFilter( request, response );
    }
}