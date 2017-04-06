package org.chiwooplatform.sample.message;

import java.util.Map;

import org.chiwooplatform.web.supports.ConverterUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author yourname <yourname@your.email>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("map")
public class Geolocation
    extends Resource {

    private String uri;

    private String data;

    private Float latitude;

    private Float longitude;

    private Float altitude;

    private Integer zoom;

    private String thumbnailUrl;

    private String embeddedUrl;

    public Map<String, Object> toMap()
        throws Exception {
        return ConverterUtils.toMap( this );
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

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData( String data ) {
        this.data = data;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude( Float latitude ) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude( Float longitude ) {
        this.longitude = longitude;
    }

    /**
     * @return the altitude
     */
    public Float getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude( Float altitude ) {
        this.altitude = altitude;
    }

    /**
     * @return the zoom
     */
    public Integer getZoom() {
        return zoom;
    }

    /**
     * @param zoom the zoom to set
     */
    public void setZoom( Integer zoom ) {
        this.zoom = zoom;
    }

    /**
     * @return the thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * @param thumbnailUrl the thumbnailUrl to set
     */
    public void setThumbnailUrl( String thumbnailUrl ) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * @return the embeddedUrl
     */
    public String getEmbeddedUrl() {
        return embeddedUrl;
    }

    /**
     * @param embeddedUrl the embeddedUrl to set
     */
    public void setEmbeddedUrl( String embeddedUrl ) {
        this.embeddedUrl = embeddedUrl;
    }
}