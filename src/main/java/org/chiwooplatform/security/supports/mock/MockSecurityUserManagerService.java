package org.chiwooplatform.security.supports.mock;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.model.ParameterMap;
import org.chiwooplatform.security.core.PermissionResolver;
import org.chiwooplatform.security.core.UserAuthoritiesResolver;
import org.chiwooplatform.security.core.UserPrincipal;
import org.chiwooplatform.security.core.UserPrincipalResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockSecurityUserManagerService
    implements UserPrincipalResolver, UserAuthoritiesResolver, PermissionResolver {

    private final transient Logger logger = LoggerFactory.getLogger( MockSecurityUserManagerService.class );

    @Autowired
    private MockUserDetailsManager userManager;

    public MockSecurityUserManagerService() {
    }

    @Override
    public UserPrincipal getUserPrincipal( ParameterMap param )
        throws AuthenticationException {
        String token = param.getString( Constants.TOKEN );
        UserPrincipal user = userManager.getUserByToken( token );
        return user;
    }

    public UserPrincipal getUser( ParameterMap param )
        throws UsernameNotFoundException {
        String token = param.getString( Constants.TOKEN );
        UserPrincipal user = userManager.getUserByToken( token );
        return user;
    }

    @Override
    public boolean hasPermission( ParameterMap param ) {
        logger.debug( "args: {}", param );
        final String token = param.getString( Constants.TOKEN );
        final String permCd = param.getString( Constants.PERM_CODE, "" );
        MockUser user = userManager.getUserByToken( token );
        if ( user == null || user.getAuthorities() == null ) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for ( GrantedAuthority ga : authorities ) {
            if ( permCd.equals( ga.getAuthority() ) ) {
                return true;
            }
        }
        logger.debug( "Has not permission. permCd: {}", permCd );
        return false;
    }

    public Collection<? extends GrantedAuthority> getAuthorities( ParameterMap param ) {
        return null;
    }
}
