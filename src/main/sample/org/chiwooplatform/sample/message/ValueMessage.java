package org.chiwooplatform.sample.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("message")
public class ValueMessage {

    private String key;

    private String value;

    public ValueMessage() {
    }

    public ValueMessage( String key, String value ) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey( String key ) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }
}
