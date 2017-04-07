package org.chiwooplatform.web.message;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;
import java.util.Locale.Category;

import org.chiwooplatform.sample.SampleApplication;
import org.chiwooplatform.web.AbstractTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class MessageSourceTests
    extends AbstractTests {

    @Autowired
    private MessageSourceAccessor messageAccessor;

    @Test
    public void testMessage()
        throws Exception {
        assertNotNull( messageAccessor );
        logger.info( "{}", messageAccessor.getMessage( "application.id", Locale.getDefault( Category.DISPLAY ) ) );
        logger.info( "{}", messageAccessor.getMessage( "application.id", "NotFound", Locale.US ) );
        logger.info( "{}", messageAccessor.getMessage( "application.id", "NotFound", Locale.KOREA ) );
    }
    
    @Test
    public void testValidationMessage()
        throws Exception {
        assertNotNull( messageAccessor );
        logger.info( "{}", messageAccessor.getMessage( "validation.code.cdId", Locale.getDefault( Category.DISPLAY ) ) );
        logger.info( "{}", messageAccessor.getMessage( "validation.code.cdVal", "NotFound", Locale.US ) );
        logger.info( "{}", messageAccessor.getMessage( "validation.code.cdVal", "NotFound", Locale.KOREA ) );
    }
}
