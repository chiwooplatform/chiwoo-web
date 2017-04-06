package org.chiwooplatform.sample.message;

import java.io.Serializable;
import java.util.List;

import org.chiwooplatform.context.supports.LanMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("responseMessage")
public class ResponseMessage
    implements Serializable {

    private static final long serialVersionUID = 715187561600168554L;

    private ResponseHeader header;

    public final ResponseHeader getHeader() {
        return header;
    }

    public final void setHeader( ResponseHeader header ) {
        this.header = header;
    }

    // ~ COM : 코드 -------------------------------------------------------------
    private List<LanMessage> lanMessages;

    /**
     * @return the lanMessages
     */
    public List<LanMessage> getLanMessages() {
        return lanMessages;
    }

    /**
     * @param lanMessages the lanMessages to set
     */
    public void setLanMessages( List<LanMessage> lanMessages ) {
        this.lanMessages = lanMessages;
    }

    private Code code;

    public Code getCode() {
        return code;
    }

    public void setCode( Code code ) {
        this.code = code;
    }

    private List<Code> codes;

    public List<Code> getCodes() {
        return this.codes;
    }

    public void setCodes( List<Code> codes ) {
        this.codes = codes;
    }

    // ~ COM : 키워드 -------------------------------------------------------------
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

    private List<Keyword> keywords;

    /**
     * @return the keywords
     */
    public List<Keyword> getKeywords() {
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords( List<Keyword> keywords ) {
        this.keywords = keywords;
    }

    // ~ RSC : 텍스트 -------------------------------------------------------------
    private Text text;

    /**
     * @return the text
     */
    public Text getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText( Text text ) {
        this.text = text;
    }

    private List<Text> texts;

    /**
     * @return the texts
     */
    public List<Text> getTexts() {
        return texts;
    }

    /**
     * @param texts the texts to set
     */
    public void setTexts( List<Text> texts ) {
        this.texts = texts;
    }

    // ~ RSC : 링크 -------------------------------------------------------------
    private Link link;

    /**
     * @return the link
     */
    public Link getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink( Link link ) {
        this.link = link;
    }

    private List<Link> links;

    /**
     * @return the links
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks( List<Link> links ) {
        this.links = links;
    }

    // ~ RSC : 맵 -------------------------------------------------------------
    private Geolocation map;

    /**
     * @return the map
     */
    public Geolocation getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap( Geolocation map ) {
        this.map = map;
    }

    private List<Geolocation> maps;

    /**
     * @return the maps
     */
    public List<Geolocation> getMaps() {
        return maps;
    }

    /**
     * @param maps the maps to set
     */
    public void setMaps( List<Geolocation> maps ) {
        this.maps = maps;
    }
}