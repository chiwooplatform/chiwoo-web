package org.chiwooplatform.security.supports.mock;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.model.ParameterMap;
import org.chiwooplatform.security.core.UserPrincipal;
import org.chiwooplatform.security.core.UserPrincipalResolver;

public class MockUserPrincipalResolver
    implements UserPrincipalResolver {

    @Autowired
    private MockUserDetailsManager userManager;

    @Override
    public UserPrincipal getUserPrincipal( ParameterMap param )
        throws AuthenticationException {
        String token = param.getString( Constants.TOKEN );
        UserPrincipal user = userManager.getUserByToken( token );
        return user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities( ParameterMap param ) {
        return null;
    }
}
