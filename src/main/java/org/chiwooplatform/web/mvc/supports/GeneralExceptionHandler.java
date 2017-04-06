package org.chiwooplatform.web.mvc.supports;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.web.exception.GeneralException;
import org.chiwooplatform.web.exception.client.BadRequestException;
import org.chiwooplatform.web.exception.client.InvalidMessageException;
import org.chiwooplatform.web.exception.client.NotAcceptableException;
import org.chiwooplatform.web.message.ErrorMessage;
import org.chiwooplatform.web.message.ItemError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GeneralExceptionHandler {

    static private final transient Logger LOGGER = LoggerFactory.getLogger( GeneralExceptionHandler.class );

    private static final String VALID_MESSAGE_PREFIX = Constants.VALID_MESSAGE_PREFIX;

    protected final Logger logger = LoggerFactory.getLogger( getClass() );

    @Autowired
    private MessageSourceAccessor messageAccessor;

    /**
     * A single place to customize the response body of all Exception types.
     * <p>
     * The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE} request attribute and creates a
     * {@link ResponseEntity} from the given body, headers, and status.
     * 
     * @param ex the exception
     * @param body the body for the response
     * @param headers the headers for the response
     * @param status the response status
     * @param request the current request
     */
    protected ResponseEntity<Object> handleExceptionInternal( Exception ex, Object body, HttpHeaders headers,
                                                              HttpStatus status, WebRequest request ) {
        // LOGGER.debug( "handleExceptionInternal body:{} error: {}", body, ex.getMessage() );
        // LOGGER.debug( "ex.getClass().getName(): {}", ex.getClass().getName() );
        if ( HttpStatus.INTERNAL_SERVER_ERROR.equals( status ) ) {
            request.setAttribute( WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST );
        }
        return new ResponseEntity<Object>( body, headers, status );
    }

    private static List<ItemError> tranformItemErrors( MessageSourceAccessor messageAccessor, final String objectName,
                                                       List<ObjectError> objectErrors ) {
        final String lang = LocaleContextHolder.getLocale().getLanguage(); 
        List<ItemError> errors = new ArrayList<>();
        for ( ObjectError objectError : objectErrors ) {
            ItemError item = new ItemError();
            if ( objectError instanceof FieldError ) {
                FieldError err = (FieldError) objectError;
                String defaultMessage = err.getDefaultMessage();
                //                LOGGER.debug( "objectName: {} fieldId: {}, code: {}", objectName,  err.getField(), err.getCode() );
                //                LOGGER.debug( "err.errorMessage(): {}", defaultMessage );
                //                LOGGER.debug( "err.getRejectedValue(): {}", err.getRejectedValue() );
                //                LOGGER.debug( "err.toString(): {}", err.toString() );
                String errorMessage = null;
                if ( defaultMessage.startsWith( VALID_MESSAGE_PREFIX ) ) {
                    LOGGER.debug( "found: VALID_MESSAGE_PREFIX: {}", messageAccessor.getMessage( defaultMessage ) );
                    errorMessage = messageAccessor.getMessage( defaultMessage, defaultMessage );
                } else {
                    final String fieldKey = VALID_MESSAGE_PREFIX + objectName + "." + err.getField();
                    LOGGER.debug( "lang:{}, fieldKey: {}", lang, fieldKey );
                    errorMessage = messageAccessor.getMessage( fieldKey,
                                                               "'" + err.getField() + "' is " + defaultMessage );
                }
                item.setResource( err.getObjectName() );
                item.setItemld( err.getField() );
                item.setCode( err.getCode() );
                item.setMessage( errorMessage );
            } else {
                item.setResource( objectError.getObjectName() );
                item.setItemld( objectError.getObjectName() );
                item.setCode( objectError.getCode() );
                item.setMessage( objectError.getDefaultMessage() );
            }
            errors.add( item );
        }
        return errors;
    }

    @ExceptionHandler({
        BadRequestException.class,
        // org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException.class,
        HttpRequestMethodNotSupportedException.class,
        HttpMediaTypeNotSupportedException.class,
        HttpMediaTypeNotAcceptableException.class,
        MissingPathVariableException.class,
        MissingServletRequestParameterException.class,
        ServletRequestBindingException.class,
        ConversionNotSupportedException.class,
        TypeMismatchException.class,
        HttpMessageNotReadableException.class,
        HttpMessageNotWritableException.class,
        MethodArgumentNotValidException.class,
        MissingServletRequestPartException.class,
        BindException.class,
        NoHandlerFoundException.class,
        AsyncRequestTimeoutException.class })
    public final ResponseEntity<Object> handleException( Exception e, WebRequest request ) {
        logger.debug( "handleBadRequestException" );
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String msg = messageAccessor.getMessage( "exception.message." + e.getClass().getSimpleName(), e.getMessage() );
        ErrorMessage message = new ErrorMessage();
        message.setMessage( httpStatus.getReasonPhrase() );
        message.setStatus( httpStatus.value() );
        message.setDetailMessage( msg );
        message.setTransactionId( (Long) request.getAttribute( Constants.TXID, WebRequest.SCOPE_REQUEST ) );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON_UTF8 );
        return handleExceptionInternal( e, message, headers, httpStatus, request );
    }

    //    @ExceptionHandler({ BadRequestException.class, HttpMessageNotReadableException.class })
    //    protected ResponseEntity<Object> handleBadRequestException( Exception e, WebRequest request ) {
    //        
    //    }
    @ExceptionHandler({ AuthenticationException.class })
    protected ResponseEntity<Object> handleAuthenticationException( AuthenticationException e, WebRequest request ) {
        logger.debug( "handleAuthenticationException" );
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorMessage message = new ErrorMessage();
        message.setMessage( httpStatus.getReasonPhrase() );
        message.setDetailMessage( e.getMessage() );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON_UTF8 );
        return handleExceptionInternal( e, message, headers, httpStatus, request );
    }

    @ExceptionHandler({ AccessDeniedException.class })
    protected ResponseEntity<Object> handleAccessDeniedException( AccessDeniedException e, WebRequest request ) {
        logger.debug( "handleAccessDeniedException" );
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ErrorMessage message = new ErrorMessage();
        message.setMessage( httpStatus.getReasonPhrase() );
        message.setDetailMessage( e.getMessage() );
        message.setTransactionId( (Long) request.getAttribute( Constants.TXID, WebRequest.SCOPE_REQUEST ) );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON_UTF8 );
        return handleExceptionInternal( e, message, headers, httpStatus, request );
    }

    @ExceptionHandler({ GeneralException.class })
    protected ResponseEntity<Object> handleGeneralException( GeneralException ge, WebRequest request ) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        logger.debug( "handleGeneralException" );
        ErrorMessage message = ge.getErrorMessage();
        if ( request instanceof HttpServletRequest ) {
            HttpServletRequest req = (HttpServletRequest) request;
            message.setUrl( req.getRequestURL().toString() );
            message.setTransactionId( (Long) request.getAttribute( Constants.TXID, WebRequest.SCOPE_REQUEST ) );
        } else if ( request instanceof ServletWebRequest ) {
            ServletWebRequest req = (ServletWebRequest) request;
            message.setUrl( req.getRequest().getRequestURL().toString() );
            message.setTransactionId( (Long) req.getAttribute( Constants.TXID, WebRequest.SCOPE_REQUEST ) );
        }
        Exception e = ge.getException();
        logger.debug( "ge.getException(): " + e.getClass().getName() );
        message.setDetailMessage( e.getMessage() );
        //        if ( e instanceof DataAccessException ) {
        //            message.setMessage( messageAccessor.getMessage( "exception.message.dataAccessException",
        //                                                            "Error occured while data accessing." ) );
        //        } else 
        if ( e instanceof InvalidMessageException ) {
            httpStatus = HttpStatus.BAD_REQUEST;
            InvalidMessageException ire = (InvalidMessageException) e;
            final String objectName = ire.getObjectName();
            Errors errors = ire.getErrors();
            message.setStatus( HttpStatus.BAD_REQUEST.value() );
            message.setMessage( messageAccessor.getMessage( "exception.message.invalidRequestException", "" ) );
            List<ItemError> itemErrors = tranformItemErrors( messageAccessor, objectName, errors.getAllErrors() );
            message.setFieldErrors( itemErrors );
        } else if ( e instanceof BadRequestException ) {
            httpStatus = HttpStatus.BAD_REQUEST;
            message.setStatus( httpStatus.value() );
            message.setMessage( messageAccessor.getMessage( "exception.message.badRequestException", "" ) );
            // BadRequestException bre = (BadRequestException) e;
            // Errors errors = bre.getErrors();
            // List<ItemError> itemErrors = tranformItemErrors( messageAccessor, errors.getAllErrors() );
            // LOGGER.debug( "itemErrors: " + itemErrors);
            // message.setFieldErrors( itemErrors );
        } else if ( e instanceof NotAcceptableException ) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            message.setStatus( httpStatus.value() );
            message.setMessage( messageAccessor.getMessage( "exception.message.notAcceptableException", "" ) );
        } else if ( e instanceof MethodArgumentNotValidException ) {
            httpStatus = HttpStatus.BAD_REQUEST;
            MethodArgumentNotValidException mae = (MethodArgumentNotValidException) e;
            String objectName = mae.getBindingResult().getTarget().getClass().getSimpleName();
            message.setStatus( httpStatus.value() );
            message.setMessage( messageAccessor.getMessage( "exception.message.methodArgumentNotValidException", "" ) );
            BindingResult br = mae.getBindingResult();
            List<ItemError> list = tranformItemErrors( messageAccessor, objectName, br.getAllErrors() );
            message.setFieldErrors( list );
        } else {
            logger.warn( "Can not catch the Exception: '" + e.getClass().getName() + "'" );
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON_UTF8 );
        return handleExceptionInternal( e, message, headers, httpStatus, request );
    }
}
