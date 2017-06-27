package org.chiwooplatform.security.supports;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * Unlike AnonymousAuthenticationFilter, authentication information is not stored in the session store.
 * 
 * Below is an example of setting AnonymousSessionlessAuthenticationFilter on HttpSecurity component.
 * <pre>
public class SecurityConfiguration
    extends WebSecurityConfigurerAdapter {

    protected void configure( HttpSecurity http )
        throws Exception {
        http.addFilterBefore( new AnonymousSessionlessAuthenticationFilter(), SessionManagementFilter.class );
    }
    
}
 * </pre>
 */
public class AnonymousSessionlessAuthenticationFilter
    extends AnonymousAuthenticationFilter {

    static private final String KEY = "anonymous";

    public AnonymousSessionlessAuthenticationFilter() {
        super( KEY );
    }

    protected Authentication createAuthentication( HttpServletRequest request ) {
        AnonymousAuthenticationToken authentication = new AnonymousAuthenticationToken( KEY, getPrincipal(),
                                                                                        getAuthorities() );
        return authentication;
    }
}
