package org.chiwooplatform.web;

import org.chiwooplatform.sample.SampleApplication;
import org.chiwooplatform.sample.message.RequestMessage;
import org.chiwooplatform.web.supports.ConverterUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = { "org.chiwooplatform.web.mvc.supports" })
public abstract class AbstractWebTests<T>
    extends AbstractTests {

    static private ObjectMapper OBJECTMAPPER;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter jsonConverter;

    abstract protected T model();

    protected RequestMessage requestMessage( Object target )
        throws Exception {
        RequestMessage message = new RequestMessage();
        ConverterUtils.setProperty( message, target );
        return message;
    }

    protected String requestBody( Object object )
        throws JsonProcessingException {
        return OBJECTMAPPER.writeValueAsString( object );
    }

    private String token = "X";

    protected final String token() {
        return this.token;
    }

    @Test
    public void contextLoads() {
    }

    @Before
    public void setUp() {
        if ( jsonConverter != null && OBJECTMAPPER == null ) {
            OBJECTMAPPER = jsonConverter.getObjectMapper();
            OBJECTMAPPER.enable( SerializationFeature.INDENT_OUTPUT );
        }
    }
}
