package org.chiwooplatform.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

import org.chiwooplatform.security.core.PermissionResolver;
import org.chiwooplatform.security.core.UserPrincipalResolver;
import org.chiwooplatform.security.supports.AnonymousSessionlessAuthenticationFilter;
import org.chiwooplatform.security.supports.RestAuthenticationFailureHandler;
import org.chiwooplatform.security.supports.RestAuthenticationFilter;
import org.chiwooplatform.security.supports.RestAuthenticationProvider;
import org.chiwooplatform.security.supports.RestAuthenticationSuccessHandler;
import org.chiwooplatform.security.supports.TokenPermissionEvaluator;
import org.chiwooplatform.web.supports.DefaultCorsConfiguration;
 

public abstract class RestSecurityConfigurationAdapter
    extends WebSecurityConfigurerAdapter {

    public RestSecurityConfigurationAdapter() {
        super( true ); // if you want to disable all of the defaults within Spring Security
        // org.springframework.security.access.intercept.AbstractSecurityInterceptor
    }

    private static final String[] EXCLUDED_WEB_STATIC_RESOURCES = new String[] {
        "/",
        "/static/**",
        "/assets/**",
        "/resources/**",
        "/favicon.ico",
        "/css/**",
        "/js/**" };

    @Override
    public void configure( WebSecurity web )
        throws Exception {
        String[] uris = webStaticUris();
        if ( uris == null ) {
            uris = EXCLUDED_WEB_STATIC_RESOURCES;
        }
        web.ignoring().antMatchers( HttpMethod.OPTIONS ).antMatchers( uris );
        //  Apply-EL TO JSP-VIEW
        // DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        // handler.setPermissionEvaluator( permissionEvaluator() );
        // web.expressionHandler( handler );
    }

    abstract protected String[] webStaticUris();

    abstract protected String authenticatedUri();

    @Override
    protected void configure( HttpSecurity http )
        throws Exception {
        DefaultCorsConfiguration cors = new DefaultCorsConfiguration();
        http.csrf().disable();
        http.cors().configurationSource( cors );
        // http.addFilter( new WebAsyncManagerIntegrationFilter() ).exceptionHandling();
        http.headers().and().sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        // .securityContext().and()
        // http.requestCache().and().anonymous();
        // .servletApi().and()
        // .apply(new DefaultLoginPageConfigurer<HttpSecurity>()).and()
        // .logout();
        // .antMatchers( EXCLUDED_URI_PATTERNS ).permitAll().and();
        http.authorizeRequests().antMatchers( authenticatedUri() ).authenticated().anyRequest().permitAll();
        http.addFilterBefore( new AnonymousSessionlessAuthenticationFilter(), SessionManagementFilter.class );
        http.addFilterBefore( restFilter(), UsernamePasswordAuthenticationFilter.class );
    }

    public RestAuthenticationFilter restFilter()
        throws Exception {
        final RestAuthenticationFilter filter = new RestAuthenticationFilter( authenticatedUri() );
        // filter.setFilterProcessesUrl( "/api/**" );
        // filter.setExcludUrlPatterns( EXCLUDED_URI_PATTERNS );
        filter.setAuthenticationManager( authenticationManager() );
        filter.setAuthenticationSuccessHandler( new RestAuthenticationSuccessHandler() );
        filter.setAuthenticationFailureHandler( new RestAuthenticationFailureHandler() );
        return filter;
    }

    @Bean
    abstract public UserPrincipalResolver userPrincipalResolver();

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final RestAuthenticationProvider authenticationProvider = new RestAuthenticationProvider( userPrincipalResolver() );
        return authenticationProvider;
    }

    @Bean
    abstract public PermissionResolver permissionResolver();

    @Bean
    public TokenPermissionEvaluator permissionEvaluator() {
        final TokenPermissionEvaluator permissionEvaluator = new TokenPermissionEvaluator( permissionResolver() );
        return permissionEvaluator;
    }
}
