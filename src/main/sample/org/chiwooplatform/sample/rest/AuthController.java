package org.chiwooplatform.sample.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.ContextHolder;
import org.chiwooplatform.context.annotation.Loggable;
import org.chiwooplatform.sample.message.ValueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yourname <yourname@your.email>
 */
@Loggable
@RestController
public class AuthController {

    protected static final String BASE_URI = "/com/auths";

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @RequestMapping(value = BASE_URI + "/{id}", method = RequestMethod.GET, consumes = {
        MediaType.APPLICATION_JSON_VALUE })
    @PreAuthorize("hasPermission(#token, 'API_ComAuth.get')")
    public ResponseEntity<?> get( @RequestHeader(Constants.AUTH_TOKEN) String token, @PathVariable("id") String id,
                                  @RequestParam Map<String, Object> param )
        throws Exception {
        logger.debug( "tXID: {}, userno: {}, principal: {}", ContextHolder.tXID(), ContextHolder.userno(),
                      ContextHolder.principal() );
        ValueMessage msg = new ValueMessage( "principal", ContextHolder.principal().getName() );
        return new ResponseEntity<>( msg, HttpStatus.OK );
    }

    @RequestMapping(value = BASE_URI + "/query", method = RequestMethod.GET, consumes = {
        MediaType.APPLICATION_JSON_VALUE })
    @PreAuthorize("hasPermission(#token, 'API_ComAuth.query')")
    public ResponseEntity<?> query( @RequestHeader(Constants.AUTH_TOKEN) String token,
                                    @RequestParam Map<String, Object> param )
        throws Exception {
        ValueMessage msg = new ValueMessage( "principal", ContextHolder.principal().getName() );
        return new ResponseEntity<>( msg, HttpStatus.OK );
    }
}
