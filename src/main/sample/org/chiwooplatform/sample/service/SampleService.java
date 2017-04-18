/**
 * @author seonbo.shim
 * @version 1.0, 2017-04-12
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.sample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.supports.LanMessage;
import org.chiwooplatform.context.supports.LocaleMessageSource;

@Service
public class SampleService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<LanMessage> getMessages( String pattern, String locale ) {
        List<LanMessage> messages = redisTemplate.execute( new RedisCallback<List<LanMessage>>() {

            @Override
            public List<LanMessage> doInRedis( RedisConnection connection )
                throws DataAccessException {
                ScanOptions options = ScanOptions.scanOptions().match( pattern ).count( 1 ).build();
                Cursor<Map.Entry<byte[], byte[]>> entries = connection.hScan( LocaleMessageSource.key( locale )
                                                                                                 .getBytes(),
                                                                              options );
                List<LanMessage> result = new ArrayList<LanMessage>();
                if ( entries != null )
                    while ( entries.hasNext() ) {
                        Map.Entry<byte[], byte[]> entry = entries.next();
                        LanMessage msg = new LanMessage();
                        msg.setLocale( locale );
                        msg.setCode( StringUtils.toEncodedString( entry.getKey(),
                                                                  Charset.forName( Constants.DEFAULT_CHARSET ) ) );
                        msg.setMessage( StringUtils.toEncodedString( entry.getValue(),
                                                                     Charset.forName( Constants.DEFAULT_CHARSET ) ) );
                        result.add( msg );
                    }
                return result;
            }
        } );
        return messages;
    }
}
