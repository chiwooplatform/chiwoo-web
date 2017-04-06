/**
 * @author seonbo.shim
 * @version 1.0, 2017-03-31
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.context.supports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcTemplateMessageSource
    extends LocaleMessageSource {

    private JdbcTemplate jdbcTemplate;

    /**
     * @param jdbcTemplate
     */
    public void setJdbcTemplate( JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @see org.chiwooplatform.context.supports.LocaleMessageSource#loadMessages()
     */
    @Override
    protected void loadMessages() {
        List<LanMessage> messages = jdbcTemplate.query( "select code, locale, message from COM_I18N_MESSAGE where use_yn = 1",
                                                        new RowMapper<LanMessage>() {

                                                            @Override
                                                            public LanMessage mapRow( ResultSet rs, int rowNum )
                                                                throws SQLException {
                                                                LanMessage msg = new LanMessage();
                                                                msg.setCode( rs.getString( "code" ) );
                                                                msg.setLocale( rs.getString( "locale" ) );
                                                                msg.setMessage( rs.getString( "message" ) );
                                                                return msg;
                                                            }
                                                        } );
        for ( LanMessage msg : messages ) {
            getRedisTemplate().opsForHash().putIfAbsent( key( msg.getLocale() ), msg.getCode(), msg.getMessage() );
            logger.debug( "msg: {}", msg );
        }
    }

    static private final char NL = '\n';

    static private String lastModifiedSQL() {
        StringBuilder ret = new StringBuilder();
        ret.append( "select  upd_dtm " );
        ret.append( NL ).append( "from    COM_I18N_MESSAGE" );
        ret.append( NL ).append( "order by upd_dtm desc" );
        ret.append( NL ).append( "limit 1" );
        return ret.toString();
    }

    static private final String LAST_MODIFIED_SQL = lastModifiedSQL();

    /**
     * @see org.chiwooplatform.context.supports.LocaleMessageSource#getLastModified()
     */
    @Override
    protected long getLastModified() {
        Date lastModified = jdbcTemplate.queryForObject( LAST_MODIFIED_SQL, Date.class );
        if ( lastModified != null ) {
            return lastModified.getTime();
        }
        return 0;
    }

    /**
     * @see org.chiwooplatform.context.supports.LocaleMessageSource#reloadMessages(java.lang.Object)
     */
    @Override
    protected void reloadMessages( Object value ) {
        List<LanMessage> messages = jdbcTemplate.query( "select code, locale, message from COM_I18N_MESSAGE where use_yn = 1 and upd_dtm > ?",
                                                        new Object[] { value },
                                                        ( rs,
                                                          row ) -> new LanMessage().code( rs.getString( "code" ) )
                                                                                   .locale( rs.getString( "locale" ) )
                                                                                   .message( rs.getString( "message" ) ) );
        for ( LanMessage msg : messages ) {
            getRedisTemplate().opsForHash().put( key( msg.getLocale() ), msg.getCode(), msg.getMessage() );
            logger.debug( "msg: {}", msg );
        }
    }
}
