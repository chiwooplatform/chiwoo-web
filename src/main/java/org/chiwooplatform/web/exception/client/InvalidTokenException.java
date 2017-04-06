package org.chiwooplatform.web.exception.client;

import org.chiwooplatform.web.message.ErrorMessage;
import org.springframework.http.HttpStatus;

public class InvalidTokenException
    // extends AuthenticationException
    extends RuntimeException {

    private static final long serialVersionUID = 5463442450167301420L;

    private final ErrorMessage message = new ErrorMessage();

    private final Exception exception;

    public InvalidTokenException( final String url, Long transactionId ) {
        this( url, null, transactionId );
    }

    public InvalidTokenException( final String url, HttpStatus httpStatus, Long transactionId ) {
        super( "Invalid token error." );
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