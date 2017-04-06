/**
 * @author seonbo.shim
 * @version 1.0, 2017-03-29
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.web;

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
        MDC.put( "TXID", Long.toString( System.currentTimeMillis() ) );
    }

    @After
    public void removeMDC() {
        MDC.remove( "TXID" );
    }
}
