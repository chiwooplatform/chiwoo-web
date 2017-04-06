package org.chiwooplatform.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.web.message.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private boolean rest( String contentType ) {
        if ( contentType == null ) {
            return false;
        }
        if ( contentType.startsWith( MediaType.APPLICATION_JSON_VALUE )
            || contentType.startsWith( MediaType.APPLICATION_JSON_UTF8_VALUE ) ) {
            return true;
        }
        return false;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleError404( HttpServletRequest request, HttpServletResponse response,
                                  NoHandlerFoundException ex ) {
        String contentType = request.getHeader( HttpHeaders.CONTENT_TYPE );
        if ( rest( contentType ) ) {
            ErrorMessage message = new ErrorMessage();
            Object txid = request.getAttribute( Constants.TXID );
            if ( txid != null ) {
                message.setTransactionId( (Long) txid );
            }
            message.setUrl( ex.getRequestURL() );
            message.setStatus( HttpStatus.NOT_FOUND.value() );
            message.setMessage( "Resource not found for HTTP request with URI" );
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType( MediaType.APPLICATION_JSON_UTF8 );
            return new ResponseEntity<Object>( message, headers, HttpStatus.NOT_FOUND );
        } else {
            ModelAndView mav = new ModelAndView( "/errors/404" );
            mav.addObject( "exception", ex );
            return mav;
        }
    }
}