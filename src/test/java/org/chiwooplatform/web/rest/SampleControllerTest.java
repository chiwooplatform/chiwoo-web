/**
 * @author seonbo.shim
 * @version 1.0, 2017-03-29
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.web.rest;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.sample.message.Code;
import org.chiwooplatform.sample.message.Keyword;
import org.chiwooplatform.sample.message.Link;
import org.chiwooplatform.sample.message.RequestMessage;
import org.chiwooplatform.sample.message.Resource;
import org.chiwooplatform.sample.message.Text;
import org.chiwooplatform.sample.rest.SampleController;
import org.chiwooplatform.web.AbstractWebTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class SampleControllerTest
    extends AbstractWebTests<Resource> {

    @Autowired
    private SampleController controller;

    @Test
    public void test_beansIsNotNull()
        throws Exception {
        assertNotNull( mockMvc );
        assertNotNull( controller );
    }

    /**
     * @see org.chiwooplatform.web.AbstractWebTests#model()
     */
    @Override
    protected Resource model() {
        Resource model = new Resource();
        return model;
    }

    /**
     * {@link SampleController#validateCode(RequestMessage, Long, javax.servlet.http.HttpServletRequest)}
     *
     * @throws Exception
     */
    @Test
    public void test_validateCode()
        throws Exception {
        final String uri = SampleController.BASE_URI + "/validate-code";
        Code code = new Code();
        code.setCdId( 111 );
        code.setCdName( "코드 명" );
        // code.setCdVal( "EMP01" );
        String content = requestBody( requestMessage( code ) );
        logger.debug( "content: {}", content );
        ResultActions actions = mockMvc.perform( post( uri ).contentType( MediaType.APPLICATION_JSON )
                                                            .header( Constants.AUTH_TOKEN, token() )
                                                            .locale( Locale.KOREA ).param( "language", "ko" )
                                                            .param( "language", "en" )
                                                            .requestAttr( Constants.TXID, System.currentTimeMillis() )
                                                            .content( content ) );
        actions.andExpect( status().isCreated() ).andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andDo( print() );
    }

    /**
     * {@link SampleController#validateText(RequestMessage, Long, javax.servlet.http.HttpServletRequest)}
     *
     * @throws Exception
     */
    @Test
    public void test_validateText()
        throws Exception {
        final String uri = SampleController.BASE_URI + "/validate-text";
        Text text = new Text();
        // text.setResrcId( "RSC1973129873129" );
        text.setResrcCdValue( "TEXT" );
        text.setStatusCdValue( "REG" );
        text.setSubject( "Subject 제목 입니다." );
        // text.setQuotationCd( 1 );
        text.setMarkdown( "markdown" );
        Keyword keyword = new Keyword();
        // keyword.setClsId( 1001 );
        keyword.setKeyword( "호남" );
        text.setKeyword( keyword );
        String content = requestBody( requestMessage( text ) );
        logger.debug( "content: {}", content );
        ResultActions actions = mockMvc.perform( put( uri ).contentType( MediaType.APPLICATION_JSON )
                                                           .header( Constants.AUTH_TOKEN, token() )
                                                           .requestAttr( Constants.TXID, System.currentTimeMillis() )
                                                           .content( content ) );
        actions.andExpect( status().isOk() ).andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andDo( print() );
    }
    /**
     * {@link SampleController#validateLink(RequestMessage, Long, javax.servlet.http.HttpServletRequest)}
     *
     * @throws Exception
     */
    @Test
    public void test_validateLink()
        throws Exception {
        final String uri = SampleController.BASE_URI + "/validate-link";
        Link link = new Link();
        link.setResrcId( "RSC1973129873129" );
        link.setResrcCdValue( "LINK" );
        link.setStatusCdValue( "REG" );
        link.setSubject( "Subject 제목 입니다." );
        link.setDomain( "domain" );
        link.setSubject( "subject" );
        link.setUri( "http://uri" );
        String content = requestBody( requestMessage( link ) );
        logger.debug( "content: {}", content );
        ResultActions actions = mockMvc.perform( post( uri ).contentType( MediaType.APPLICATION_JSON )
                                                            .header( Constants.AUTH_TOKEN, token() )
                                                            .requestAttr( Constants.TXID, System.currentTimeMillis() )
                                                            .content( content ) );
        actions.andExpect( status().isCreated() ).andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andDo( print() );
    }

    /**
     * {@link SampleController#lanMessages(Long, String, String, javax.servlet.http.HttpServletRequest)}
     *
     * @throws Exception
     */
    @Test
    public void test_lanMessages()
        throws Exception {
        final String uri = SampleController.BASE_URI + "/lan-messages";
        ResultActions actions = mockMvc.perform( get( uri ).contentType( MediaType.APPLICATION_JSON )
                                                           .header( Constants.AUTH_TOKEN, token() )
                                                           .requestAttr( Constants.TXID, System.currentTimeMillis() )
                                                           .param( "language", "en" ) );
        actions.andExpect( status().isOk() ).andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andDo( print() );
    }
}
