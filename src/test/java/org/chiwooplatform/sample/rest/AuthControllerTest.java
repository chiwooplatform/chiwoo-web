package org.chiwooplatform.sample.rest;

import static org.junit.Assert.assertNotNull;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.sample.AbstractWebTests;
import org.junit.Test;

public class AuthControllerTest
    extends AbstractWebTests<Object> {

    @Autowired
    private AuthController controller;

    private String id = "test1001";

    /**
     * @see org.chiwooplatform.sample.AbstractWebTests#model()
     */
    @Override
    protected Object model() {
        Object model = new Object();
        return model;
    }

    @Test
    public void loaderTest() {
        logger.info( "mockMvc: {}", mockMvc );
        assertNotNull( mockMvc );
        assertNotNull( controller );
    }

    /**
     * {@link AuthController#get}
     * 
     * @throws Exception
     */
    @Test
    public void ut1001_get()
        throws Exception {
        final String uri = AuthController.BASE_URI + "/{id}";
        String id = this.id;
        ResultActions actions = mockMvc.perform( get( uri, id ).contentType( MediaType.APPLICATION_JSON )
                                                               .header( Constants.AUTH_TOKEN, token() ) );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isOk() );
        actions.andDo( print() );
    }

    /**
     * {@link AuthController#query}
     * 
     * @throws Exception
     */
    @Test
    public void ut1002_query()
        throws Exception {
        final String uri = AuthController.BASE_URI + "/query";
        ResultActions actions = mockMvc.perform( get( uri ).contentType( MediaType.APPLICATION_JSON )
                                                           .header( Constants.AUTH_TOKEN, token() )
        // @formatter:off
                                                           // .param( lang(), "ko" ) 
        // @formatter:on
        );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isOk() );
        actions.andDo( print() );
    }
}
