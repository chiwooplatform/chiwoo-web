package org.chiwooplatform.sample.message;

import org.chiwooplatform.web.annotation.AppCodes;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author yourname <yourname@your.email>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("social")
public class Social
    extends Resource {

    @NotEmpty
    @JsonProperty(required = true)
    private String linkSrc;

    @AppCodes(parent = 6100, description = "소셜 유형 코드")
    private Integer socialCd;

    private String socialCdValue;

    /**
     * @return the linkSrc
     */
    public String getLinkSrc() {
        return linkSrc;
    }

    /**
     * @param linkSrc the linkSrc to set
     */
    public void setLinkSrc( String linkSrc ) {
        this.linkSrc = linkSrc;
    }

    /**
     * @return the socialCd
     */
    public Integer getSocialCd() {
        return socialCd;
    }

    /**
     * @param socialCd the socialCd to set
     */
    public void setSocialCd( Integer socialCd ) {
        this.socialCd = socialCd;
    }

    /**
     * @return the socialCdValue
     */
    public String getSocialCdValue() {
        return socialCdValue;
    }

    /**
     * @param socialCdValue the socialCdValue to set
     */
    public void setSocialCdValue( String socialCdValue ) {
        this.socialCdValue = socialCdValue;
    }
}
