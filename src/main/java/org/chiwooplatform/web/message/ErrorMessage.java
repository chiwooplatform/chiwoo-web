package org.chiwooplatform.web.message;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.chiwooplatform.context.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "transactionId", "message", "detailMessage", "url", "status", "fieldErrors", "timestamp" })
public class ErrorMessage
    implements Serializable {

    private static final long serialVersionUID = -7174809893610457006L;

    @JsonFormat(pattern = Constants.DEFAULT_TIMESTAMP_FORMAT, timezone = Constants.LOCAL_TIMEZONE)
    private Date timestamp = Calendar.getInstance().getTime();

    private Long transactionId;

    private int status;

    private String message;

    private String detailMessage;

    private List<ItemError> fieldErrors;

    private String url;

    public final Date getTimestamp() {
        return timestamp;
    }

    public final Long getTransactionId() {
        return transactionId;
    }

    public final void setTransactionId( Long transactionId ) {
        this.transactionId = transactionId;
    }

    public final int getStatus() {
        return status;
    }

    public final void setStatus( int status ) {
        this.status = status;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage( String message ) {
        this.message = message;
    }

    public final String getDetailMessage() {
        return detailMessage;
    }

    public final void setDetailMessage( String detailMessage ) {
        this.detailMessage = detailMessage;
    }

    public final List<ItemError> getFieldErrors() {
        return fieldErrors;
    }

    public final void setFieldErrors( List<ItemError> fieldErrors ) {
        this.fieldErrors = fieldErrors;
    }

    public final String getUrl() {
        return url;
    }

    public final void setUrl( String url ) {
        this.url = url;
    }
}
