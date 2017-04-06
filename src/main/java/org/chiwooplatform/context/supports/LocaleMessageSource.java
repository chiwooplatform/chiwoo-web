package org.chiwooplatform.context.supports;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.chiwooplatform.context.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.data.redis.core.StringRedisTemplate;

public abstract class LocaleMessageSource
    extends AbstractMessageSource {

    private static final String I18N_NAMESPACE = Constants.I18N_NAMESPACE;

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    private final TimestampHolder holder = new TimestampHolder();

    private boolean concurrentRefresh = true;

    private long cacheMillis = -1;

    protected long getCacheMillis() {
        return this.cacheMillis;
    }

    public void setConcurrentRefresh( boolean concurrentRefresh ) {
        this.concurrentRefresh = concurrentRefresh;
    }

    public void setCacheSeconds( int cacheSeconds ) {
        this.cacheMillis = ( cacheSeconds * 1000 );
    }

    abstract protected void loadMessages();

    abstract protected long getLastModified();

    abstract protected void reloadMessages( Object value );

    @Autowired
    private StringRedisTemplate redisTemplate;

    protected StringRedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    public LocaleMessageSource() {
        super();
    }

    static public String key( String locale ) {
        return I18N_NAMESPACE + "_" + locale;
    }

    /**
     * @see org.springframework.context.support.AbstractMessageSource#resolveCode(java.lang.String, java.util.Locale)
     */
    @Override
    protected MessageFormat resolveCode( String code, Locale locale ) {
        refreshMessages();
        String lang = locale.getLanguage();
        String message = (String) redisTemplate.opsForHash().get( key( lang ), code );
        return createMessageFormat( message, locale );
    }

    @PostConstruct
    public void messageLoadProcess() {
        loadMessages();
    }

    /**
     * @return the boolean value which is refreshed or not.
     */
    protected boolean refreshMessages() {
        if ( !this.concurrentRefresh ) {
            return false;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final long comparisonTimeMillis = currentTimeMillis - getCacheMillis();
        if ( !holder.isTriggered( comparisonTimeMillis ) ) {
            // currentTime is in interval time range.
            logger.trace( "currentTime is in interval time range." );
            return false;
        }
        logger.trace( "Triggered Checking Lan-Localization." );
        long lastModified = getLastModified();
        // At this point, we need to refresh...
        if ( lastModified > holder.getLastModified() ) {
            reloadMessages( new Date( holder.getLastModified() ) );
            holder.setLastModified( lastModified );
            return true;
        }
        return false;
    }

    // protected abstract Messages getMessages();
    //    /**
    //     * Messages bundle
    //     */
    //    protected static final class Messages {
    //
    //        /* <code, <locale, message>> */
    //        private Map<String, Map<Locale, String>> messages;
    //
    //        public void addMessage( String code, Locale locale, String msg ) {
    //            if ( messages == null )
    //                messages = new HashMap<String, Map<Locale, String>>();
    //            Map<Locale, String> data = messages.get( code );
    //            if ( data == null ) {
    //                data = new HashMap<Locale, String>();
    //                messages.put( code, data );
    //            }
    //            data.put( locale, msg );
    //        }
    //
    //        public String getMessage( String code, Locale locale ) {
    //            Map<Locale, String> data = messages.get( code );
    //            return data != null ? data.get( locale ) : null;
    //        }
    //    }
    protected class TimestampHolder {

        // private final ReentrantLock refreshLock = new ReentrantLock();
        private volatile long refreshTimestamp = -1;

        private volatile long lastModified = -1;

        TimestampHolder() {
        }

        /**
         * @return the refreshTimestamp
         */
        public long getRefreshTimestamp() {
            return refreshTimestamp;
        }

        /**
         * @return the lastModified
         */
        public long getLastModified() {
            return lastModified;
        }

        /**
         * @param lastModified the lastModified to set
         */
        public void setLastModified( long lastModified ) {
            this.lastModified = lastModified;
        }

        public boolean isTriggered( long comparisonTimeMillis ) {
            if ( refreshTimestamp > comparisonTimeMillis ) {
                return false;
            }
            this.refreshTimestamp = System.currentTimeMillis();
            return true;
        }
    }
}
