package org.chiwooplatform.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.chiwooplatform.context.model.ParameterMap;

public interface AuthUserResolver {

    AuthUser getUser( ParameterMap param )
        throws UsernameNotFoundException;
}
