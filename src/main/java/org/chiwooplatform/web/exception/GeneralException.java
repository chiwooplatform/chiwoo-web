package org.chiwooplatform.web.exception;

import org.springframework.http.HttpStatus;

import org.chiwooplatform.web.exception.client.BadRequestException;
import org.chiwooplatform.web.exception.client.InvalidMessageException;
import org.chiwooplatform.web.message.ErrorMessage;

public class GeneralException
    extends RuntimeException {

    private static final long serialVersionUID = -6850550861177276252L;

    private final ErrorMessage message = new ErrorMessage();

    private final Exception exception;

    public GeneralException( Exception e ) {
        this( e, null, null );
    }

    public GeneralException( Exception e, HttpStatus httpStatus ) {
        this( e, httpStatus, null );
    }

    public GeneralException( Exception e, HttpStatus httpStatus, Long transactionId ) {
        super();
        int statusCode = httpStatus.value();
        if ( statusCode >= 400 && statusCode < 500 ) {
            // System.out.println( "e.getClass().getName(): " + e.getClass().getName() );
            if ( e instanceof InvalidMessageException ) {
                InvalidMessageException ire = (InvalidMessageException) e;
                this.exception = ire;
            } else {
                BadRequestException bre = new BadRequestException( e.getMessage(), e, httpStatus, transactionId );
                this.exception = bre;
            }
        } else {
            this.exception = e;
        }
        message.setTransactionId( transactionId );
        message.setDetailMessage( e.getMessage() );
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
