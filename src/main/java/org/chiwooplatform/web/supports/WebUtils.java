package org.chiwooplatform.web.supports;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.web.exception.GeneralException;
import org.springframework.http.HttpStatus;

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
            throw new RuntimeException( "path valiable is null, abd sourceUri is " + sourceUri );
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
     * value 값이 없다면 defaultValue 값을 리턴 한다.
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static String nvl( String value, String defaultValue ) {
        if ( value != null ) {
            return value;
        } else {
            return defaultValue;
        }
    }

    /**
     * value 값이 없다면 defaultValue 값을 리턴 한다.
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static Integer nvl( Integer value, Integer defaultValue ) {
        if ( value != null ) {
            return value;
        } else {
            return defaultValue;
        }
    }

    /**
     * value 파라미터 값을 integer 객체의 값으로 변환한 값을 리턴
     *
     * @param value 소스 객체 값
     * @return integer 형 변환 객체 값
     */
    public static Integer getInteger( Object value ) {
        if ( value == null ) {
            return null;
        }
        if ( value instanceof Integer ) {
            return (Integer) value;
        }
        if ( value instanceof String ) {
            return Integer.valueOf( (String) value );
        } else if ( value instanceof BigDecimal ) {
            return ( (BigDecimal) value ).intValue();
        } else if ( value instanceof BigInteger ) {
            return ( (BigInteger) value ).intValue();
        } else if ( value instanceof Long ) {
            return ( (Long) value ).intValue();
        }
        return (Integer) value;
    }

    /**
     * value 파라미터 값을 Long 객체의 값으로 변환한 값을 리턴
     *
     * @param value 소스 객체 값
     * @return Long 형 변환 객체 값
     */
    public static Long getLong( Object value ) {
        if ( value == null ) {
            return null;
        }
        if ( value instanceof Integer ) {
            return (Long) value;
        } else if ( value instanceof BigDecimal ) {
            return ( (BigDecimal) value ).longValue();
        } else if ( value instanceof BigInteger ) {
            return ( (BigInteger) value ).longValue();
        } else if ( value instanceof Long ) {
            return ( (Long) value ).longValue();
        }
        return (Long) value;
    }

    /**
     * @param value
     * @return delimiterValues
     */
    public static String[] getDelimiterValues( final String value ) {
        if ( value == null ) {
            return new String[] { "0" };
        }
        StringTokenizer token = new StringTokenizer( value, Constants.DEFAULT_DELIMITER );
        LinkedList<String> set = new LinkedList<>();
        while ( token.hasMoreTokens() ) {
            set.add( token.nextToken() );
        }
        if ( set.size() < 1 ) {
            return new String[] { value };
        }
        String[] arrays = set.toArray( new String[set.size()] );
        return arrays;
    }

    /**
     * Controller 의 최종 예외를 판단 하여 GeneralException 을 던집니다.
     *
     * @param e
     */
    public static GeneralException throwException( Exception e ) {
        if ( e instanceof GeneralException ) {
            return (GeneralException) e;
        }
        return new GeneralException( e, HttpStatus.INTERNAL_SERVER_ERROR );
    }
}
