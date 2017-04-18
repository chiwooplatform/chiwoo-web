/**
 * @author seonbo.shim
 * @version 1.0, 2017-04-12
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.web.exception;

import org.springframework.http.HttpStatus;

import org.chiwooplatform.web.message.ErrorMessage;

public class ServiceException
    extends RuntimeException {

    private static final long serialVersionUID = -2895658406140769167L;

    private final ErrorMessage message = new ErrorMessage();

    private final Exception exception;

    public ServiceException( String msg ) {
        super();
        this.exception = null;
        message.setMessage( msg );
        message.setDetailMessage( "{" + this.getClass().getName() + ": " + msg + "}" );
        message.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
    }

    public ServiceException( Exception e ) {
        this( e, HttpStatus.INTERNAL_SERVER_ERROR, null );
    }

    public ServiceException( Exception e, HttpStatus httpStatus ) {
        this( e, httpStatus, null );
    }

    public ServiceException( Exception e, HttpStatus httpStatus, Long transactionId ) {
        super();
        this.exception = e;
        message.setTransactionId( transactionId );
        message.setMessage( e.getMessage() );
        message.setDetailMessage( "{" + e.getClass().getName() + ": " + e.getMessage() + "}" );
        if ( httpStatus != null ) {
            message.setStatus( httpStatus.value() );
        }
    }

    public final Exception getException() {
        return exception;
    }

    public final ErrorMessage getErrorMessage() {
        return message;
    }

    public String getMessage() {
        if ( this.exception != null ) {
            return this.exception.getMessage();
        } else {
            return super.getMessage();
        }
    }
}
