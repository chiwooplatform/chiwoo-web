package org.chiwooplatform.sample;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.ContextHolder;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public abstract class AbstractTests {

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    private final Integer AUTH_USER_ID = -100001;

    protected Integer userid() {
        return this.AUTH_USER_ID;
    }

    @Before
    public void setMDC() {
        Long tXID = ContextHolder.tXID( true );
        MDC.put( Constants.TXID, Long.toString( tXID ) );
        logger.info( "init tXID" );
    }

    @After
    public void removeMDC() {
        ContextHolder.removeTXID();
        MDC.remove( Constants.TXID );
    }
}
