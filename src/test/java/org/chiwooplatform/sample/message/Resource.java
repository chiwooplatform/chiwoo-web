package org.chiwooplatform.sample.message;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.web.annotation.AppCodes;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXISTING_PROPERTY, property = "resrcCdValue", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Text.class, name = "TEXT"),
    @JsonSubTypes.Type(value = Geolocation.class, name = "MAP"),
    @JsonSubTypes.Type(value = Link.class, name = "LINK") })
@JsonRootName("resource")
public class Resource {

    @NotEmpty
    protected String resrcId;

    @NotEmpty
    private String subject;

    @Range(min = 0, max = 9999)
    protected Integer visibleOrdno;

    @AppCodes(parent = 5200, description = "리소스 유형 코드")
    protected Integer resrcCd;

    @NotEmpty
    protected String resrcCdValue;

    @AppCodes(parent = 5273, description = "리소스 상태 코드")
    protected Integer statusCd;

    @NotNull
    @Length(max = 6)
    protected String statusCdValue;

    protected String source; // 출처

    private Integer registerId;

    @JsonFormat(pattern = Constants.DEFAULT_TIMESTAMP_FORMAT, timezone = Constants.LOCAL_TIMEZONE)
    private Date regDtm;

    private Integer modifierId;

    @JsonFormat(pattern = Constants.DEFAULT_TIMESTAMP_FORMAT, timezone = Constants.LOCAL_TIMEZONE)
    private Date updDtm;

    /**
     * @return the resrcId
     */
    public String getResrcId() {
        return resrcId;
    }

    /**
     * @param resrcId the resrcId to set
     */
    public void setResrcId( String resrcId ) {
        this.resrcId = resrcId;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject( String subject ) {
        this.subject = subject;
    }

    /**
     * @return the visibleOrdno
     */
    public Integer getVisibleOrdno() {
        return visibleOrdno;
    }

    /**
     * @param visibleOrdno the visibleOrdno to set
     */
    public void setVisibleOrdno( Integer visibleOrdno ) {
        this.visibleOrdno = visibleOrdno;
    }

    /**
     * @return the resrcCd
     */
    public Integer getResrcCd() {
        return resrcCd;
    }

    /**
     * @param resrcCd the resrcCd to set
     */
    public void setResrcCd( Integer resrcCd ) {
        this.resrcCd = resrcCd;
    }

    /**
     * @return the resrcCdValue
     */
    public String getResrcCdValue() {
        return resrcCdValue;
    }

    /**
     * @param resrcCdValue the resrcCdValue to set
     */
    public void setResrcCdValue( String resrcCdValue ) {
        this.resrcCdValue = resrcCdValue;
    }

    /**
     * @return the statusCd
     */
    public Integer getStatusCd() {
        return statusCd;
    }

    /**
     * @param statusCd the statusCd to set
     */
    public void setStatusCd( Integer statusCd ) {
        this.statusCd = statusCd;
    }

    /**
     * @return the statusCdValue
     */
    public String getStatusCdValue() {
        return statusCdValue;
    }

    /**
     * @param statusCdValue the statusCdValue to set
     */
    public void setStatusCdValue( String statusCdValue ) {
        this.statusCdValue = statusCdValue;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource( String source ) {
        this.source = source;
    }

    /**
     * @return the registerId
     */
    public Integer getRegisterId() {
        return registerId;
    }

    /**
     * @param registerId the registerId to set
     */
    public void setRegisterId( Integer registerId ) {
        this.registerId = registerId;
    }

    /**
     * @return the regDtm
     */
    public Date getRegDtm() {
        return regDtm;
    }

    /**
     * @param regDtm the regDtm to set
     */
    public void setRegDtm( Date regDtm ) {
        this.regDtm = regDtm;
    }

    /**
     * @return the modifierId
     */
    public Integer getModifierId() {
        return modifierId;
    }

    /**
     * @param modifierId the modifierId to set
     */
    public void setModifierId( Integer modifierId ) {
        this.modifierId = modifierId;
    }

    /**
     * @return the updDtm
     */
    public Date getUpdDtm() {
        return updDtm;
    }

    /**
     * @param updDtm the updDtm to set
     */
    public void setUpdDtm( Date updDtm ) {
        this.updDtm = updDtm;
    }
}