package org.chiwooplatform.web.exception.client;

import org.springframework.http.HttpStatus;

import org.chiwooplatform.context.ContextHolder;

/**
 * <pre>
 * 클라이언트 요청 전문 형식 오류가 감지 될 경우 BadRequestException 예외가 주관 하여 처리 합니다.
 * </pre>
 *
 * @author aider
 */
@SuppressWarnings("serial")
public class BadRequestException
    extends TraceableException {

    private HttpStatus httpStatus;

    /**
     * @param message error message
     * @param cause Ecxeption
     */
    public BadRequestException( String message, Throwable cause ) {
        this( message, cause, ContextHolder.tXID() );
    }

    /**
     * @param message error message
     * @param cause Ecxeption
     * @param tXID Request transaction ID
     */
    public BadRequestException( String message, Throwable cause, Long tXID ) {
        this( message, cause, HttpStatus.BAD_REQUEST, tXID );
    }

    /**
     * 
     * @param message error message
     * @param cause Ecxeption
     * @param httpStatus org.springframework.http.HttpStatus
     * @param tXID Request transaction ID
     */
    public BadRequestException( String message, Throwable cause, HttpStatus httpStatus, Long tXID ) {
        super( message, cause, tXID );
        this.httpStatus = httpStatus;
    }

    public final HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
