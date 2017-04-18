/**
 * @author seonbo.shim
 * @version 1.0, 2017-04-13
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.security;

import java.util.Arrays;

public class UserPrincipal
    implements java.security.Principal {

    private String name;

    private Integer userSeq;

    private String[] authorities;

    public UserPrincipal( String name, Integer id ) {
        this.name = name;
        this.userSeq = id;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "UserPrincipal [name=" ).append( name ).append( ", userSeq=" ).append( userSeq )
               .append( ", authorities=" ).append( Arrays.toString( authorities ) ).append( "]" );
        return builder.toString();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public Integer getUserSeq() {
        return userSeq;
    }

    /**
     * @return the roles
     */
    public String[] getAuthorities() {
        return authorities;
    }

    /**
     * @param roles the roles to set
     */
    public void setAuthorities( String... roles ) {
        this.authorities = roles;
    }
}
