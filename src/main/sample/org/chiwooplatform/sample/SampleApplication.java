package org.chiwooplatform.sample;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.supports.JdbcTemplateMessageSource;
import org.chiwooplatform.context.supports.LoggingAspect;
import org.chiwooplatform.context.supports.ThrowableHandlerAspect;
import org.chiwooplatform.web.mvc.supports.TransactionLoggingFilter;
import org.chiwooplatform.web.supports.RequestValidator;

@SpringBootApplication(exclude = {
    SessionAutoConfiguration.class,
    ValidationAutoConfiguration.class,
    MessageSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class })
public class SampleApplication {

    public static void main( String[] args ) {
        SpringApplication.run( SampleApplication.class, args );
    }

    @Configuration
    public static class ApplicationConfiguration {

        @Bean
        public LoggingAspect loggingAspect() {
            LoggingAspect loggingAspect = new LoggingAspect();
            return loggingAspect;
        }

        @Bean
        public ThrowableHandlerAspect throwableHandlerAspect() {
            ThrowableHandlerAspect throwableHandlerAspect = new ThrowableHandlerAspect();
            return throwableHandlerAspect;
        }
    }

    @ComponentScan(basePackages = "org.chiwooplatform.web.mvc.supports")
    @Configuration
    public static class MvcConfiguration
        extends WebMvcConfigurerAdapter {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Bean
        public MessageSource messageSource() {
            final JdbcTemplateMessageSource messageSource = new JdbcTemplateMessageSource();
            messageSource.setCacheSeconds( 30 ); //reload messages every 30 seconds
            messageSource.setJdbcTemplate( jdbcTemplate );
            return messageSource;
        }

        @Bean
        public MessageSourceAccessor messageAccessor() {
            final MessageSourceAccessor messageAccessor = new MessageSourceAccessor( messageSource() );
            return messageAccessor;
        }

        @Bean
        public LocaleResolver localeResolver() {
            final CookieLocaleResolver localeResolver = new CookieLocaleResolver();
            localeResolver.setDefaultLocale( Locale.US );
            return localeResolver;
        }

        @Bean
        public LocaleChangeInterceptor localeChangeInterceptor() {
            final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
            localeChangeInterceptor.setParamName( Constants.LANGUAGE );
            return localeChangeInterceptor;
        }

        @Bean
        public LocalValidatorFactoryBean localValidatorFactoryBean() {
            LocalValidatorFactoryBean localValidator = new LocalValidatorFactoryBean();
            localValidator.setValidationMessageSource( messageSource() );
            return localValidator;
        }

        @Bean
        public RequestValidator requestValidator() {
            RequestValidator requestValidator = new RequestValidator();
            requestValidator.setLocalValidatorFactory( localValidatorFactoryBean() );
            return requestValidator;
        }

        @Override
        public void addInterceptors( InterceptorRegistry registry ) {
            registry.addInterceptor( localeChangeInterceptor() );
        }

        @Override
        public Validator getValidator() {
            return localValidatorFactoryBean();
        }

        @Bean
        public FilterRegistrationBean transactionLoggingFilter() {
            FilterRegistrationBean registrationBean = new FilterRegistrationBean( new TransactionLoggingFilter() );
            return registrationBean;
        }
    }
}
