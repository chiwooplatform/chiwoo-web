package org.chiwooplatform.security.web.supports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class AntUrisRequestMatcher
    implements RequestMatcher {

    private Collection<AntPathRequestMatcher> matchers = new ArrayList<>();

    public AntUrisRequestMatcher( String... uris ) {
        super();
        Objects.requireNonNull( uris, "URIs is requried." );
        for ( String uri : uris ) {
            matchers.add( new AntPathRequestMatcher( uri ) );
        }
    }

    @Override
    public boolean matches( HttpServletRequest request ) {
        for ( AntPathRequestMatcher matches : matchers ) {
            if ( matches.matches( request ) ) {
                return true;
            }
        }
        return false;
    }
}
