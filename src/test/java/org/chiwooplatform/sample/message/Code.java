package org.chiwooplatform.sample.message;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.chiwooplatform.context.Constants;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

/** 
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("code")
public class Code
    implements Serializable {

    @NotNull
    @Range(min = 1, max = 9999)
    private Integer cdId;

    @NotNull
    private String cdName;

    @NotNull
    private String cdVal;

    private Integer parentId;

    private String path;

    private String descr;

    private Boolean useYn;

    private Integer ordno;

    private Integer registerId;

    @JsonFormat(pattern = Constants.DEFAULT_TIMESTAMP_FORMAT, timezone = Constants.LOCAL_TIMEZONE)
    private Date regDtm;

    private Integer modifierId;

    @JsonFormat(pattern = Constants.DEFAULT_TIMESTAMP_FORMAT, timezone = Constants.LOCAL_TIMEZONE)
    private Date updDtm;

    private String extVal;

    private String extVal2;

    /**
     * @return the cdId
     */
    public Integer getCdId() {
        return cdId;
    }

    /**
     * @param cdId the cdId to set
     */
    public void setCdId( Integer cdId ) {
        this.cdId = cdId;
    }

    /**
     * @return the cdName
     */
    public String getCdName() {
        return cdName;
    }

    /**
     * @param cdName the cdName to set
     */
    public void setCdName( String cdName ) {
        this.cdName = cdName;
    }

    /**
     * @return the cdVal
     */
    public String getCdVal() {
        return cdVal;
    }

    /**
     * @param cdVal the cdVal to set
     */
    public void setCdVal( String cdVal ) {
        this.cdVal = cdVal;
    }

    /**
     * @return the parentId
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId( Integer parentId ) {
        this.parentId = parentId;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath( String path ) {
        this.path = path;
    }

    /**
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * @param descr the descr to set
     */
    public void setDescr( String descr ) {
        this.descr = descr;
    }

    /**
     * @return the useYn
     */
    public Boolean getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn( Boolean useYn ) {
        this.useYn = useYn;
    }

    /**
     * @return the ordno
     */
    public Integer getOrdno() {
        return ordno;
    }

    /**
     * @param ordno the ordno to set
     */
    public void setOrdno( Integer ordno ) {
        this.ordno = ordno;
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

    /**
     * @return the extVal
     */
    public String getExtVal() {
        return extVal;
    }

    /**
     * @param extVal the extVal to set
     */
    public void setExtVal( String extVal ) {
        this.extVal = extVal;
    }

    /**
     * @return the extVal2
     */
    public String getExtVal2() {
        return extVal2;
    }

    /**
     * @param extVal2 the extVal2 to set
     */
    public void setExtVal2( String extVal2 ) {
        this.extVal2 = extVal2;
    }
}