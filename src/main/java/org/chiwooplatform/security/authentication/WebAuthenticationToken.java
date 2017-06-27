package org.chiwooplatform.security.authentication;

import java.util.Collection;

import java.security.Principal;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.chiwooplatform.security.core.UserPrincipal;
 

@SuppressWarnings("serial")
public class WebAuthenticationToken
    extends AbstractAuthenticationToken {

    private final String token;

    private final String sessionId;

    /**
     * @param token auth token
     * @param sessionId ssession id of HttpSession.
     * @param authorities Collection of granted authorities.
     */
    public WebAuthenticationToken( String token, String sessionId,
                                    Collection<? extends GrantedAuthority> authorities ) {
        super( authorities );
        this.token = token;
        this.sessionId = sessionId;
    }

    /**
     *
     * @param token auth token
     * @param sessionId ssession id of HttpSession.
     * @param principal This is can be used to represent any entity, such as an individual, a corporation, and a login id.
     * @param authorities Collection of granted authorities.
     */
    public WebAuthenticationToken( String token, String sessionId, UserPrincipal principal,
                                    Collection<? extends GrantedAuthority> authorities ) {
        super( authorities );
        this.token = token;
        this.sessionId = sessionId;
        this.principal = principal;
        super.setAuthenticated( true );
    }

    private UserPrincipal principal;

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return this.principal;
    }

    public String getName() {
        Object src = this.getPrincipal();
        if ( src instanceof UserPrincipal ) {
            return ( (UserPrincipal) src ).getName();
        } else if ( src instanceof UserDetails ) {
            return ( (UserDetails) src ).getUsername();
        }
        if ( src instanceof Principal ) {
            return ( (Principal) src ).getName();
        }
        return ( this.getPrincipal() == null ) ? "" : this.getPrincipal().toString();
    }
}
