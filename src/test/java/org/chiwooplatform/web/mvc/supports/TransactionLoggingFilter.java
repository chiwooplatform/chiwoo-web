/**
 * @author seonbo.shim
 * @version 1.0, 2017-03-27
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.web.mvc.supports;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.web.supports.UUIDGenerator;
import org.slf4j.MDC;

/**
 * <pre>
 * {@code
   <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>WARN</level>
        </filter>
        <encoder>
            <Pattern>
              %d{yyyy-MM-dd HH:mm:ss} %X{TXID} |%-5level-%msg %class.%method:%line %xException{full} %n
            </Pattern>
        </encoder>
    </appender>}
 * </pre>
 */
public class TransactionLoggingFilter
    implements Filter {

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException {
        boolean successfulRegistration = false;
        final long tXID = UUIDGenerator.generateTXID();
        MDC.put( Constants.TXID, tXID + "" );
        request.setAttribute( Constants.TXID, tXID );
        try {
            chain.doFilter( request, response );
            successfulRegistration = true;
            // System.out.printf( "%s", MDC.get( Constants.TXID ) );
        } finally {
            if ( successfulRegistration ) {
                MDC.remove( Constants.TXID );
            }
        }
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init( FilterConfig config )
        throws ServletException {
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }
}
