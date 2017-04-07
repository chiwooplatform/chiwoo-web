package org.chiwooplatform.context.supports;

public class LanMessage {

    private String code;

    private String locale;

    private String message;

    public LanMessage() {
        super();
    }

    public LanMessage code( String code ) {
        this.code = code;
        return this;
    }

    public LanMessage locale( String locale ) {
        this.locale = locale;
        return this;
    }

    public LanMessage message( String message ) {
        this.message = message;
        return this;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "I18n [code=" + code + ", locale=" + locale + ", message=" + message + "]";
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode( String code ) {
        this.code = code;
    }

    /**
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale( String locale ) {
        this.locale = locale;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage( String message ) {
        this.message = message;
    }
}
