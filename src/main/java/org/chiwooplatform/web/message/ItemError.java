package org.chiwooplatform.web.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author aider
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemError
{
    private String resource;

    private String itemld;

    private String code;

    private String message;

    public final String getResource()
    {
        return resource;
    }

    public final void setResource( String resource )
    {
        this.resource = resource;
    }

    public final String getItemld()
    {
        return itemld;
    }

    public final void setItemld( String itemld )
    {
        this.itemld = itemld;
    }

    public final String getCode()
    {
        return code;
    }

    public final void setCode( String code )
    {
        this.code = code;
    }

    public final String getMessage()
    {
        return message;
    }

    public final void setMessage( String message )
    {
        this.message = message;
    }
}