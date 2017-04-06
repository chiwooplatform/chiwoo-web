/**
 * @author seonbo.shim
 * @version 1.0, 2017-03-29
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.sample.rest;

import static org.chiwooplatform.web.supports.WebUtils.throwException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.supports.LanMessage;
import org.chiwooplatform.context.supports.LocaleMessageSource;
import org.chiwooplatform.sample.message.Code;
import org.chiwooplatform.sample.message.Link;
import org.chiwooplatform.sample.message.RequestMessage;
import org.chiwooplatform.sample.message.ResponseMessage;
import org.chiwooplatform.sample.message.Text;
import org.chiwooplatform.web.supports.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    public static final String BASE_URI = "/samples";

    @Autowired
    private RequestValidator validator;

    @RequestMapping(value = BASE_URI + "/validate-code", method = RequestMethod.POST, consumes = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> validateCode( @RequestBody RequestMessage requestMessage,
                                           @RequestAttribute(Constants.TXID) Long tXID, HttpServletRequest request ) {
        Code code = requestMessage.getCode();
        logger.debug( "payload [{}]", code );
        validator.validate( code, tXID );
        logger.debug( "Request message is valid." );
        ResponseMessage response = new ResponseMessage();
        try {
            code.setModifierId( -101 );
            code.setUpdDtm( new Date() );
            response.setCode( code );
            return new ResponseEntity<>( response, HttpStatus.CREATED );
        } catch ( Exception e ) {
            logger.error( e.getMessage(), e );
            throw throwException( e );
        }
    }

    @RequestMapping(value = BASE_URI + "/validate-text", method = RequestMethod.PUT, consumes = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> validateText( @RequestBody RequestMessage requestMessage,
                                           @RequestAttribute(Constants.TXID) Long tXID, HttpServletRequest request ) {
        Text text = requestMessage.getText();
        logger.debug( "payload [{}]", text );
        validator.validate( text, tXID );
        logger.debug( "Request message is valid." );
        ResponseMessage response = new ResponseMessage();
        try {
            text.setModifierId( -101 );
            text.setUpdDtm( new Date() );
            response.setText( text );
            return new ResponseEntity<>( response, HttpStatus.OK );
        } catch ( Exception e ) {
            logger.error( e.getMessage(), e );
            throw throwException( e );
        }
    }

    @RequestMapping(value = BASE_URI + "/validate-link", method = RequestMethod.POST, consumes = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> validateLink( @RequestBody RequestMessage requestMessage,
                                           @RequestAttribute(Constants.TXID) Long tXID, HttpServletRequest request ) {
        Link link = requestMessage.getLink();
        logger.debug( "payload [{}]", link );
        validator.validate( link, tXID );
        logger.debug( "Request message is valid." );
        ResponseMessage response = new ResponseMessage();
        try {
            link.setModifierId( -101 );
            link.setUpdDtm( new Date() );
            response.setLink( link );
            return new ResponseEntity<>( response, HttpStatus.CREATED );
        } catch ( Exception e ) {
            logger.error( e.getMessage(), e );
            throw throwException( e );
        }
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = BASE_URI + "/lan-messages", method = RequestMethod.GET, consumes = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> lanMessages( @RequestAttribute(Constants.TXID) Long tXID,
                                          @RequestParam(name = "language", defaultValue = "ko", required = false) String locale,
                                          @RequestParam(name = "code", required = false) String code,
                                          HttpServletRequest request ) {
        ResponseMessage response = new ResponseMessage();
        final String pattern = ( code != null ? code + "*" : "*" );
        logger.debug( "pattern: {}", pattern );
        try {
            List<LanMessage> lanMessages = redisTemplate.execute( new RedisCallback<List<LanMessage>>() {

                @Override
                public List<LanMessage> doInRedis( RedisConnection connection )
                    throws DataAccessException {
                    ScanOptions options = ScanOptions.scanOptions().match( pattern ).count( 1 ).build();
                    Cursor<Map.Entry<byte[], byte[]>> entries = connection.hScan( LocaleMessageSource.key( locale )
                                                                                                     .getBytes(),
                                                                                  options );
                    List<LanMessage> result = new ArrayList<LanMessage>();
                    if ( entries != null )
                        while ( entries.hasNext() ) {
                            Map.Entry<byte[], byte[]> entry = entries.next();
                            LanMessage msg = new LanMessage();
                            msg.setLocale( locale );
                            msg.setCode( StringUtils.toEncodedString( entry.getKey(),
                                                                      Charset.forName( Constants.DEFAULT_CHARSET ) ) );
                            msg.setMessage( StringUtils.toEncodedString( entry.getValue(),
                                                                         Charset.forName( Constants.DEFAULT_CHARSET ) ) );
                            result.add( msg );
                        }
                    return result;
                }
            } );
            response.setLanMessages( lanMessages );
            return new ResponseEntity<>( response, HttpStatus.OK );
        } catch ( Exception e ) {
            logger.error( e.getMessage(), e );
            throw throwException( e );
        }
    }
}
