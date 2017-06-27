package org.chiwooplatform.security.supports.mock;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.chiwooplatform.context.model.ParameterMap;
import org.chiwooplatform.security.core.UserPrincipal;

public class MockUser
    implements UserPrincipal {

    final ParameterMap param = new ParameterMap();

    private UserDetails user;

    private Integer userNo;

    public MockUser( UserDetails user, Integer userNo ) {
        super();
        this.user = user;
        this.userNo = userNo;
    }

    public String getToken() {
        return user.getPassword();
    }

    @Override
    public String getUserid() {
        return user.getUsername();
    }

    @Override
    public Integer getUserno() {
        return this.userNo;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    public UserDetails getUser() {
        return this.user;
    }

    public String getString( String key ) {
        return param.getString( key );
    }

    public Integer getInteger( String key ) {
        return param.getInteger( key );
    }

    public Boolean getBoolean( String key ) {
        return param.getBoolean( key );
    }

    public void put( String key, Object value ) {
        param.put( key, value );
    }
}
