package org.chiwooplatform.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import org.chiwooplatform.context.model.ParameterMap;

public interface SecurityUserManagerService
    extends AuthUserResolver {

    boolean hasPermission( ParameterMap param );

    Collection<? extends GrantedAuthority> getAuthorities( ParameterMap param );
}
