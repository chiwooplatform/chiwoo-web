package org.chiwooplatform.sample.dam.jdbc;

import java.util.Date;
import java.util.List;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import org.chiwooplatform.context.supports.LanMessage;
import org.chiwooplatform.sample.AbstractTests;
import org.chiwooplatform.sample.SampleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class JdbcTemplateTests
    extends AbstractTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test_jdbcTemplate() {
        Date value = DateUtils.addSeconds( new Date( System.currentTimeMillis() ), -60 );
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        logger.info( "value: {}", sdf.format( value ) );
        List<LanMessage> messages = jdbcTemplate.query( "select code, locale, message from COM_I18N_MESSAGE where use_yn = 1 and upd_dtm >= ? ",
                                                        new Object[] { value },
                                                        ( rs,
                                                          row ) -> new LanMessage().code( rs.getString( "code" ) )
                                                                                   .locale( rs.getString( "locale" ) )
                                                                                   .message( rs.getString( "message" ) ) );
        logger.info( "messages: {}", messages );
    }
}
