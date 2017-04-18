/**
 * @author seonbo.shim
 * @version 1.0, 2017-04-17
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.web.exception.client;

import org.springframework.http.HttpStatus;

import org.chiwooplatform.context.ContextHolder;

@SuppressWarnings("serial")
public class NotFoundException
    extends TraceableException {

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    /**
     * @param message error message
     */
    public NotFoundException( String message ) {
        this( message, null, ContextHolder.tXID() );
    }

    /**
     * 
     * @param message error message
     * @param cause Ecxeption
     * @param tXID Request transaction ID
     */
    public NotFoundException( String message, Throwable cause, Long tXID ) {
        super( message, cause, tXID );
    }

    public final HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
