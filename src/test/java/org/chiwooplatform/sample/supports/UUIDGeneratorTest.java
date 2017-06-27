package org.chiwooplatform.sample.supports;

import org.chiwooplatform.context.supports.UUIDGenerator;
import org.chiwooplatform.sample.AbstractTests;
import org.junit.Test;

public class UUIDGeneratorTest
    extends AbstractTests {

    @Test
    public void test_uuid()
        throws Exception {
        for ( int i = 0; i < 10; i++ ) {
            logger.info( "UUIDGenerator.uuid(): {}", UUIDGenerator.get() );
        }
    }

    @Test
    public void test_get()
        throws Exception {
        logger.info( "UUIDGenerator.get(): {}", UUIDGenerator.get() );
    }

    @Test
    public void test_tXID()
        throws Exception {
        logger.info( "UUIDGenerator.tXID(): {}", UUIDGenerator.tXID() );
    }

    @Test
    public void test_getTXID()
        throws Exception {
        logger.info( "UUIDGenerator.getTXID(): {}", UUIDGenerator.getTXID() );
    }
}
