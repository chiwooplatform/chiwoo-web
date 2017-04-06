package org.chiwooplatform.sample.message;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.chiwooplatform.web.annotation.AppCodes;
import org.chiwooplatform.web.supports.ConverterUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("text")
public class Text
    extends Resource {

    private String markdown;

    private String text;

    @AppCodes(parent = 5225, description = "인용구 유형 코드")
    private Integer quotationCd;

    private String quotationCdValue;

    @NotNull
    @Valid
    private Keyword keyword;

    /**
     * @return the keyword
     */
    public Keyword getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword( Keyword keyword ) {
        this.keyword = keyword;
    }

    public Map<String, Object> toMap()
        throws Exception {
        return ConverterUtils.toMap( this );
    }

    /**
     * @return the markdown
     */
    public String getMarkdown() {
        return markdown;
    }

    /**
     * @param markdown the markdown to set
     */
    public void setMarkdown( String markdown ) {
        this.markdown = markdown;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText( String text ) {
        this.text = text;
    }

    /**
     * @return the quotationCd
     */
    public Integer getQuotationCd() {
        return quotationCd;
    }

    /**
     * @param quotationCd the quotationCd to set
     */
    public void setQuotationCd( Integer quotationCd ) {
        this.quotationCd = quotationCd;
    }

    /**
     * @return the quotationCdValue
     */
    public String getQuotationCdValue() {
        return quotationCdValue;
    }

    /**
     * @param quotationCdValue the quotationCdValue to set
     */
    public void setQuotationCdValue( String quotationCdValue ) {
        this.quotationCdValue = quotationCdValue;
    }
}