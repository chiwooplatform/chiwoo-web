package org.chiwooplatform.security.supports.mock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockUserDetailsManager {

    protected final Logger logger = LoggerFactory.getLogger( MockUserDetailsManager.class );

    private final Map<String, MockUser> store = new HashMap<String, MockUser>();

    public MockUserDetailsManager() {
    }

    public MockUserDetailsManager( MockUser... users ) {
        for ( MockUser user : users ) {
            this.store.put( user.getToken(), user );
        }
    }

    public MockUser getUserByToken( String token )
        throws UsernameNotFoundException {
        MockUser user = store.get( token );
        if ( user == null ) {
            throw new UsernameNotFoundException( token );
        }
        return user;
    }

    public Map<String, MockUser> getAllUsers() {
        return this.store;
    }

    public UserDetails loadUserByUsername( String username )
        throws UnsupportedOperationException {
        throw new UnsupportedOperationException( "Not allowed." );
    }
}
