package org.chiwooplatform.web.mvc.supports;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.ContextHolder;
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

    private static final String ANONYMOUS_PRINCIPAL = "anonymous";

    String principal( ServletRequest request ) {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        } catch ( Exception e ) {
            if ( request instanceof HttpServletRequest ) {
                HttpServletRequest req = (HttpServletRequest) request;
                if ( req.getUserPrincipal() != null ) {
                    return req.getUserPrincipal().getName();
                }
            }
        }
        return ANONYMOUS_PRINCIPAL;
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException {
        Long tXID = (Long) request.getAttribute( Constants.TXID );
        if ( tXID == null ) {
            tXID = ContextHolder.tXID( true );
        }
        MDC.put( Constants.TXID, tXID.toString() );
        MDC.put( Constants.PRINCIPAL, principal( request ) );
        request.setAttribute( Constants.TXID, tXID );
        try {
            chain.doFilter( request, response );
        } finally {
            ContextHolder.removeTXID();
            MDC.remove( Constants.TXID );
            MDC.remove( Constants.PRINCIPAL );
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
