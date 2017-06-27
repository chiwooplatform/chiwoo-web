package org.chiwooplatform.security.core;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import org.chiwooplatform.context.model.ParameterMap;

public interface UserAuthoritiesResolver {

    Collection<? extends GrantedAuthority> getAuthorities( ParameterMap param );
}
