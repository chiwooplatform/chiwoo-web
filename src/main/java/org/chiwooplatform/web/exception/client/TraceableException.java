package org.chiwooplatform.web.exception.client;

import java.util.Date;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public abstract class TraceableException
    extends RuntimeException {

    private Long transactionId;

    final private Date timestamp;

    private String detailMessage;

    public TraceableException( String message, Long tXID ) {
        super( message );
        this.transactionId = tXID;
        this.timestamp = new Date( System.currentTimeMillis() );
    }

    public TraceableException( String message, Throwable cause, Long tXID ) {
        super( message, cause );
        this.transactionId = tXID;
        this.timestamp = new Date( System.currentTimeMillis() );
    }

    abstract public HttpStatus getHttpStatus();

    /**
     * @return the transactionId
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId( Long transactionId ) {
        this.transactionId = transactionId;
    }

    /**
     * @return the detailMessage
     */
    public String getDetailMessage() {
        return detailMessage;
    }

    /**
     * @param detailMessage the detailMessage to set
     */
    public void setDetailMessage( String detailMessage ) {
        this.detailMessage = detailMessage;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }
}
