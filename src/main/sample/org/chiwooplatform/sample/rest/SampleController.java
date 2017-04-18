package org.chiwooplatform.sample.rest;

import static org.chiwooplatform.web.supports.WebUtils.*;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.annotation.Loggable;
import org.chiwooplatform.context.supports.LanMessage;
import org.chiwooplatform.sample.message.Code;
import org.chiwooplatform.sample.message.Link;
import org.chiwooplatform.sample.message.RequestMessage;
import org.chiwooplatform.sample.message.ResponseMessage;
import org.chiwooplatform.sample.message.Text;
import org.chiwooplatform.sample.service.SampleService;
import org.chiwooplatform.web.supports.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Loggable
@RestController
public class SampleController {

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    static final String BASE_URI = "/samples";

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
            throw generalException( e );
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
        text.setModifierId( -101 );
        text.setUpdDtm( new Date() );
        response.setText( text );
        return new ResponseEntity<>( response, HttpStatus.OK );
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
            throw generalException( e );
        }
    }

    @Autowired
    private SampleService sampleService;

    @RequestMapping(value = BASE_URI + "/lan-messages", method = RequestMethod.GET, consumes = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> lanMessages( @RequestParam(name = "lang", defaultValue = "ko", required = false) String lang,
                                          @RequestParam(name = "code", required = false) String code,
                                          HttpServletRequest request ) {
        ResponseMessage response = new ResponseMessage();
        final String pattern = ( code != null ? code + "*" : "*" );
        //        if ( !"XXX".equals( pattern ) ) {
        //            throw new IllegalArgumentException( "test" );
        //        }
        // logger.debug( "pattern: {}", pattern );
        List<LanMessage> lanMessages = sampleService.getMessages( pattern, lang );
        response.setLanMessages( lanMessages );
        return new ResponseEntity<>( response, HttpStatus.OK );
    }
}
