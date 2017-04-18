package org.chiwooplatform.sample.message;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.junit4.SpringRunner;

import org.chiwooplatform.sample.AbstractTests;
import org.chiwooplatform.sample.SampleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        logger.info( "{}",
                     messageAccessor.getMessage( "validation.Code.cdId", Locale.getDefault( Category.DISPLAY ) ) );
        logger.info( "{}", messageAccessor.getMessage( "validation.Code.cdVal", "NotFound", Locale.US ) );
        logger.info( "{}", messageAccessor.getMessage( "validation.Code.cdVal", "NotFound", Locale.KOREA ) );
    }
}
