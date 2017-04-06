package org.chiwooplatform.web.exception.client;

/**
 * <pre>
 * 파일 업로드 등의 요청 시 허용되지 않은 파일이 감지 되었을 때 발생하는 Exception
 * </pre>
 * 
 * @author jinwoo.yuk
 */
@SuppressWarnings("serial")
public class NotAcceptableException
    extends RuntimeException
{
    public NotAcceptableException()
    {
        super();
    }
}
