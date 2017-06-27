package org.chiwooplatform.security.core;

import org.springframework.security.core.AuthenticationException;

import org.chiwooplatform.context.model.ParameterMap;

public interface UserPrincipalResolver {

    UserPrincipal getUserPrincipal( ParameterMap param )
        throws AuthenticationException;
}
