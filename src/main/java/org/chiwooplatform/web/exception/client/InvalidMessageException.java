package org.chiwooplatform.web.exception.client;

import org.springframework.validation.Errors;

/**
 * <pre>
 * 클라이언트 요청 전문 형식 오류가 감지 될 경우 InvalidRequestException 예외가 주관 하여 처리 합니다.
 * </pre>
 *
 * @author aider
 */
@SuppressWarnings("serial")
public class InvalidMessageException
    extends RuntimeException {

    private Errors errors;

    private String objectName;

    public InvalidMessageException() {
        super();
    }

    public InvalidMessageException( String message, Errors errors ) {
        super( message );
        this.errors = errors;
    }

    public InvalidMessageException( String message, String objectName, Errors errors ) {
        super( message );
        this.objectName = objectName;
        this.errors = errors;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    public Errors getErrors() {
        return errors;
    }
}
