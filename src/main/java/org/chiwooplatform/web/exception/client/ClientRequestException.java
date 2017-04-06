package org.chiwooplatform.web.exception.client;

import org.springframework.http.HttpStatus;

/**
 * <pre>
 * 클라이언트 요청 커넥션 오류가 감지 될 경우 처리 합니다.
 * </pre>
 *
 * @author aider
 */
@SuppressWarnings("serial")
public class ClientRequestException
    extends RuntimeException
{
    private HttpStatus httpStatus;

    private String message;

    public ClientRequestException()
    {
        super();
    }

    public ClientRequestException( HttpStatus httpStatus, String message )
    {
        super( message );
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ClientRequestException( HttpStatus httpStatus, Throwable cause, String message )
    {
        super( message, cause );
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public final HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public final String getMessage()
    {
        return message;
    }
}
