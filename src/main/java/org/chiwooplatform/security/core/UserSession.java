package org.chiwooplatform.security.core;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSession
    implements UserPrincipal {

    private final String userid;

    private final Integer userno;

    private final UserDetails delegator;

    public UserSession( final UserDetails user, final Integer userno ) {
        super();
        this.delegator = user;
        this.userid = user.getUsername();
        this.userno = userno;
    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return this.userid;
    }

    public Integer getUserno() {
        return this.userno;
    }

    public String getUsername() {
        return delegator.getUsername();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegator.getAuthorities();
    }

    public boolean isAccountNonExpired() {
        return delegator.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return delegator.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return delegator.isCredentialsNonExpired();
    }

    public boolean isEnabled() {
        return delegator.isEnabled();
    }
}
