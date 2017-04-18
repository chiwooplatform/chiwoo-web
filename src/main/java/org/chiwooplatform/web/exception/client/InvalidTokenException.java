package org.chiwooplatform.web.exception.client;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import org.chiwooplatform.web.message.ErrorMessage;

@SuppressWarnings("serial")
public class InvalidTokenException
    extends AuthenticationException {

    private final ErrorMessage message = new ErrorMessage();

    private final Exception exception;

    public InvalidTokenException( final String url, Long tXID ) {
        this( url, null, tXID );
    }

    public InvalidTokenException( final String url, HttpStatus httpStatus, Long transactionId ) {
        super( "Authentication token is not valid." );
        this.exception = this;
        message.setMessage( ( httpStatus != null ? httpStatus.getReasonPhrase()
                                                 : HttpStatus.UNAUTHORIZED.getReasonPhrase() ) );
        message.setTransactionId( transactionId );
        message.setUrl( url );
        message.setStatus( ( httpStatus != null ? httpStatus.value() : HttpStatus.UNAUTHORIZED.value() ) );
    }

    public final Exception getException() {
        return exception;
    }

    public final ErrorMessage getErrorMessage() {
        return message;
    }
}