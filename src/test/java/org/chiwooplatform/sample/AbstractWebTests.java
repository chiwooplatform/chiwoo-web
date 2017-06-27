package org.chiwooplatform.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.chiwooplatform.context.supports.ConverterUtils;
import org.chiwooplatform.sample.message.RequestMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    private String token = "bc9dd4658e06498fb7917c12bac3f969";

    protected final String token() {
        return this.token;
    }

    protected enum USER {
                         bob("bob", "bc9dd4658e06498fb7917c12bac3f969"),
                         apple("apple", "9d787279cd3d48fa863c8dfdec255b25"),
                         banana("banana", "ccabe36b77784631b2caf30552d313e8"),
                         kssy("kssy", "d107406bab4b4daeb2e85f75f2a22aa2"),;

        String name;

        String token;

        USER( String name, String token ) {
            this.name = name;
            this.token = token;
        }

        public String user() {
            return this.name;
        }

        public String token() {
            return this.token;
        }
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
