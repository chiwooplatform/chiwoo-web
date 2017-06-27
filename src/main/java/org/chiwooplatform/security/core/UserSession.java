package org.chiwooplatform.security.core;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface UserSession
    extends UserPrincipal {
    
    public Collection<? extends GrantedAuthority> getAuthorities();
}
