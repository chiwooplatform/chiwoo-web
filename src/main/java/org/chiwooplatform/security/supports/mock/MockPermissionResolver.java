package org.chiwooplatform.security.supports.mock;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.model.ParameterMap;
import org.chiwooplatform.security.core.PermissionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockPermissionResolver
    implements PermissionResolver {

    private final transient Logger logger = LoggerFactory.getLogger( MockPermissionResolver.class );

    @Autowired
    private MockUserDetailsManager userManager;

    public MockPermissionResolver() {
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
}
