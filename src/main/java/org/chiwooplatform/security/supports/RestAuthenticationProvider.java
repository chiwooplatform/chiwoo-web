package org.chiwooplatform.security.supports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.chiwooplatform.context.model.ParameterMap;
import org.chiwooplatform.security.AuthUser;
import org.chiwooplatform.security.SecurityUserManagerService;
import org.chiwooplatform.security.UserPrincipal;
import org.chiwooplatform.security.authentication.RestAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Please use this filter for testing purposes only.
 * <pre>
    &#64;Bean
    public AuthenticationProvider authenticationProvider() {
        final RestAuthenticationProvider authenticationProvider = new RestAuthenticationProvider();
        return authenticationProvider;
    }
 * </pre>
 */
public class RestAuthenticationProvider
    implements AuthenticationProvider {

    private final transient Logger logger = LoggerFactory.getLogger( RestAuthenticationProvider.class );

    @Autowired
    private SecurityUserManagerService userManagerService;

    public RestAuthenticationProvider() {
        super();
    }

    /**
     * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate( Authentication authentication )
        throws AuthenticationException {
        if ( authentication instanceof RestAuthenticationToken ) {
            RestAuthenticationToken auth = (RestAuthenticationToken) authentication;
            final String token = auth.getToken();
            final String sessionId = auth.getSessionId();
            ParameterMap param = new ParameterMap();
            param.put( "token", token );
            param.put( "sessionId", sessionId );
            AuthUser user = userManagerService.getUser( param );
            if ( user != null ) {
                UserPrincipal userPrincipal = new UserPrincipal( user.userId(), user.userNo() );
                final RestAuthenticationToken newAuthentication = new RestAuthenticationToken( token, sessionId,
                                                                                               userPrincipal, null );
                logger.debug( "newAuthentication: {}", newAuthentication );
                return newAuthentication;
            }
        }
        return authentication;
    }

    /**
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     */
    @Override
    public boolean supports( Class<?> authentication ) {
        return ( RestAuthenticationToken.class.isAssignableFrom( authentication ) );
    }
}
