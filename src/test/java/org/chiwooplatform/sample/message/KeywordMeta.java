package org.chiwooplatform.sample.message;

import java.io.Serializable;
import java.util.Map;

import org.chiwooplatform.web.supports.ConverterUtils;

/**
 * @author yourname <yourname@your.email>
 */
public class KeywordMeta
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer clsId;

    private String keyword;

    private String clsName;

    private String descr;

    private Integer ordno;

    private Integer useYn;

    private String classPath;

    public Map<String, Object> toMap()
        throws Exception {
        return ConverterUtils.toMap( this );
    }

    /**
     * @return the clsId
     */
    public Integer getClsId() {
        return clsId;
    }

    /**
     * @param clsId the clsId to set
     */
    public void setClsId( Integer clsId ) {
        this.clsId = clsId;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword( String keyword ) {
        this.keyword = keyword;
    }

    /**
     * @return the clsName
     */
    public String getClsName() {
        return clsName;
    }

    /**
     * @param clsName the clsName to set
     */
    public void setClsName( String clsName ) {
        this.clsName = clsName;
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
     * @return the useYn
     */
    public Integer getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn( Integer useYn ) {
        this.useYn = useYn;
    }

    /**
     * @return the classPath
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     * @param classPath the classPath to set
     */
    public void setClassPath( String classPath ) {
        this.classPath = classPath;
    }
}