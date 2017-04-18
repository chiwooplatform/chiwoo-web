package org.chiwooplatform.context.supports;

import org.springframework.boot.logging.LogLevel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerWriter {

    private static final String LOGGER_NAME = "LogWriter";

    private static final Logger logger = LoggerFactory.getLogger( LOGGER_NAME );

    public static void writeToLog( LogLevel level, String message ) {
        switch ( level ) {
            case TRACE:
                logger.trace( message );
                break;
            case DEBUG:
                logger.debug( message );
                break;
            case INFO:
                logger.info( message );
                break;
            case WARN:
                logger.warn( message );
                break;
            case ERROR:
                logger.error( message );
                break;
            case FATAL:
                logger.error( message );
                break;
            default:
                logger.warn( "No suitable log level found" );
                break;
        }
    }

    /**
     * @param level LogLevel
     * @param ex Exception
     */
    public static void writeToLog( LogLevel level, Throwable ex ) {
        String message = ex.getMessage();
        switch ( level ) {
            case TRACE:
                logger.trace( message );
                break;
            case DEBUG:
                logger.debug( message );
                break;
            case INFO:
                logger.info( message );
                break;
            case WARN:
                logger.warn( message );
                break;
            case ERROR:
                logger.error( message, ex );
                break;
            case FATAL:
                logger.error( message, ex );
                break;
            default:
                logger.warn( "No suitable log level found" );
                break;
        }
    }
}
