package org.chiwooplatform.web.supports;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import org.chiwooplatform.web.exception.GeneralException;
import org.chiwooplatform.web.exception.client.NotFoundException;
 
/**
 * <pre>
 * Request 요청 데이타를 처리함에 있어 공통적으로 처리 될 수 있는 유틸리티성 기능들을 제공하는 클래스
 * </pre>
 *
 * @author aider
 */
public final class WebUtils {

    private final static String URI_REGEX = "\\{[A-z0-9]*\\}";

    /**
     * RESTFul 리소스 URI 를 리턴 한다.
     *
     * @param sourceUri pathVariables 마크업이 포함된 uri
     * @param pathVariables pathVariables 마크업 변수를 치환할 변수 배열
     * @return 리소스 uri 의 pathVariables 마크업이 실제 변수로 치환된 url
     */
    public static String genUriWithPathVariables( String sourceUri, String... pathVariables ) {
        String resultUri = null;
        if ( pathVariables != null ) {
            Matcher matcher = Pattern.compile( URI_REGEX ).matcher( sourceUri );
            int matchedCount = 0;
            while ( matcher.find() ) {
                matchedCount++;
            }
            if ( pathVariables.length == matchedCount ) {
                resultUri = sourceUri;
                for ( String pathVariable : pathVariables ) {
                    resultUri = resultUri.replaceFirst( URI_REGEX, pathVariable );
                }
            } else {
                throw new RuntimeException( "path valiables count mismatched.\nsourceUri is " + sourceUri
                    + "\npathVariables is " + Arrays.toString( pathVariables ) );
            }
        } else {
            throw new RuntimeException( "path valiable is null, and sourceUri is " + sourceUri );
        }
        return resultUri;
    }

    /**
     * RESTFul 리소스 URI 를 리턴 한다.
     *
     * @param uri pathVariables 마크업이 포함된 uri
     * @param pathVariables pathVariables 마크업 변수를 치환할 변수 배열
     * @return 리소스 uri 의 pathVariables 마크업이 실제 변수로 치환된 url
     */
    public static String genAwareUriWithPathVariables( String uri, String... pathVariables ) {
        String resultUri = uri;
        if ( pathVariables != null ) {
            Matcher matcher = Pattern.compile( URI_REGEX ).matcher( uri );
            int matchedCount = 0;
            while ( matcher.find() ) {
                matchedCount++;
            }
            if ( matchedCount > pathVariables.length ) {
                throw new RuntimeException( String.format( "path valiables count mismatched. uri is '%s' pathVariables is '%s'",
                                                           uri, Arrays.toString( pathVariables ) ) );
            } else {
                for ( int i = 0; i < matchedCount; i++ ) {
                    resultUri = resultUri.replaceFirst( URI_REGEX, pathVariables[i] );
                }
            }
        } else {
            throw new RuntimeException( String.format( "path valiable is null, abd uri is '%s'", uri ) );
        }
        return resultUri;
    }

    /**
     * Controller 의 최종 예외를 판단 하여 GeneralException 을 던집니다.
     *
     * @param e Exception
     * @return GeneralException
     */
    public static GeneralException generalException( Exception e ) {
        if ( e instanceof GeneralException ) {
            return (GeneralException) e;
        }
        return new GeneralException( e, HttpStatus.INTERNAL_SERVER_ERROR );
    }

    public static NotFoundException notFoundException() {
        return new NotFoundException( "Resource not found." );
    }
}
