package org.chiwooplatform.sample.message;

import java.util.Map;

import org.chiwooplatform.web.supports.ConverterUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author jinwoo.yuk <newcircle@brstorm.co.kr>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("link")
public class Link
    extends Resource {

    private String domain;

    private String uri;

    public Map<String, Object> toMap()
        throws Exception {
        return ConverterUtils.toMap( this );
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain( String domain ) {
        this.domain = domain;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri( String uri ) {
        this.uri = uri;
    }
}