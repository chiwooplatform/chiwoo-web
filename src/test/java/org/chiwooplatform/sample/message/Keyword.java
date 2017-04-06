package org.chiwooplatform.sample.message;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Keyword {

    @NotNull
    private Integer clsId;

    @NotEmpty()
    private String keyword;

    private String className;

    private String classPath;

    private Integer keywordPoint;

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
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName( String className ) {
        this.className = className;
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
     * @return the keywordPoint
     */
    public Integer getKeywordPoint() {
        return keywordPoint;
    }

    /**
     * @param keywordPoint the keywordPoint to set
     */
    public void setKeywordPoint( Integer keywordPoint ) {
        this.keywordPoint = keywordPoint;
    }
}
