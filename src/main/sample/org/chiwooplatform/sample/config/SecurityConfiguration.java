package org.chiwooplatform.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
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
import org.chiwooplatform.security.supports.mock.MockPermissionResolver;
import org.chiwooplatform.security.supports.mock.MockUser;
import org.chiwooplatform.security.supports.mock.MockUserDetailsManager;
import org.chiwooplatform.security.supports.mock.MockUserPrincipalResolver;
import org.chiwooplatform.web.supports.DefaultCorsConfiguration;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
    extends WebSecurityConfigurerAdapter {

    private static final String[] EXCLUDED_URI_PATTERNS = { "/signup", "/about", "/samples/**" };

    public SecurityConfiguration() {
        super( true ); // if you want to disable all of the defaults within Spring Security 
    }

    @Override
    public void configure( WebSecurity web )
        throws Exception {
        web.ignoring().antMatchers( "/static/**", "/assets/**", "/resources/**" );
        //  Apply-EL TO JSP-VIEW
        // DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        // handler.setPermissionEvaluator( permissionEvaluator() );
        // web.expressionHandler( handler );
    }

    @Override
    protected void configure( HttpSecurity http )
        throws Exception {
        DefaultCorsConfiguration cors = new DefaultCorsConfiguration();
        http.csrf().disable();
        http.cors().configurationSource( cors );
        // http.addFilter( new WebAsyncManagerIntegrationFilter() ).exceptionHandling();
        http.headers();
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ); // THIS IS FOR RESTFUL-API
        // .securityContext().and()
        http.addFilterBefore( new AnonymousSessionlessAuthenticationFilter(), SessionManagementFilter.class );
        // http.requestCache().and().anonymous();
        // .servletApi().and()
        // .apply(new DefaultLoginPageConfigurer<HttpSecurity>()).and()
        // .logout();
        http.authorizeRequests().antMatchers( EXCLUDED_URI_PATTERNS ).permitAll().and();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore( restFilter(), UsernamePasswordAuthenticationFilter.class );
    }

    protected void configure( AuthenticationManagerBuilder auth )
        throws Exception {
        auth.authenticationProvider( restAuthenticationProvider() );
    }

    @Bean
    public UserPrincipalResolver userPrincipalResolver() {
        final MockUserPrincipalResolver principalResolver = new MockUserPrincipalResolver();
        return principalResolver;
    }

    @Bean
    public PermissionResolver permissionResolver() {
        final MockPermissionResolver permissionResolver = new MockPermissionResolver();
        return permissionResolver;
    }

    @Bean
    public AuthenticationProvider restAuthenticationProvider() {
        final RestAuthenticationProvider restAuthenticationProvider = new RestAuthenticationProvider( userPrincipalResolver() );
        return restAuthenticationProvider;
    }

    @Bean
    public RestAuthenticationFilter restFilter()
        throws Exception {
        final RestAuthenticationFilter restFilter = new RestAuthenticationFilter();
        restFilter.setExcludUrlPatterns( EXCLUDED_URI_PATTERNS );
        restFilter.setAuthenticationManager( authenticationManager() );
        restFilter.setAuthenticationSuccessHandler( new RestAuthenticationSuccessHandler() );
        restFilter.setAuthenticationFailureHandler( new RestAuthenticationFailureHandler() );
        return restFilter;
    }

    //    This is for accessing MemoryDB like Redis.
    //    @Bean
    //    public HttpSessionStrategy httpSessionStrategy() {
    //        final HeaderHttpSessionStrategy httpSessionStrategy = new HeaderHttpSessionStrategy();
    //        httpSessionStrategy.setHeaderName( Constants.AUTH_TOKEN );
    //        return httpSessionStrategy;
    //    }
    @Bean
    public TokenPermissionEvaluator permissionEvaluator() {
        final TokenPermissionEvaluator permissionEvaluator = new TokenPermissionEvaluator( permissionResolver() );
        return permissionEvaluator;
    }

    //    UUIDGenerator.uuid(): bc9dd4658e06498fb7917c12bac3f969
    //    UUIDGenerator.uuid(): 9d787279cd3d48fa863c8dfdec255b25
    //    UUIDGenerator.uuid(): ccabe36b77784631b2caf30552d313e8
    //    UUIDGenerator.uuid(): d107406bab4b4daeb2e85f75f2a22aa2
    @Bean
    public MockUserDetailsManager mockUserDetailsManager() {
        return new MockUserDetailsManager( new MockUser( User.withUsername( "bob" )
                                                             .password( "bc9dd4658e06498fb7917c12bac3f969" )
                                                             .authorities( "API_ComCode.add", "API_ComCode.get",
                                                                           "API_ComCode.query", "API_ComCode.remove",
                                                                           "API_ComCode.enable", "API_ComCode.disable",
                                                                           "API_ComAuth.get" )
                                                             .build(),
                                                         100001 ),
                                           new MockUser( User.withUsername( "apple" )
                                                             .password( "9d787279cd3d48fa863c8dfdec255b25" )
                                                             .authorities( "API_ComCode.get", "API_ComCode.query",
                                                                           "API_ComCode.enable", "API_ComCode.disable" )
                                                             .build(),
                                                         100002 ),
                                           new MockUser( User.withUsername( "banana" )
                                                             .password( "ccabe36b77784631b2caf30552d313e8" )
                                                             .authorities( "API_ComCode.get", "API_ComCode.query" )
                                                             .build(),
                                                         100003 ),
                                           new MockUser( User.withUsername( "kssy" )
                                                             .password( "d107406bab4b4daeb2e85f75f2a22aa2" )
                                                             .authorities( "PORTAL_Usr.get", "PORTAL_Usr.query" )
                                                             .build(),
                                                         100004 ) );
    }
}