package org.chiwooplatform.sample.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("header")
public class ResponseHeader {

    private Integer count;

    public final Integer getCount() {
        return count;
    }

    public final void setCount( Integer count ) {
        this.count = count;
    }
}
