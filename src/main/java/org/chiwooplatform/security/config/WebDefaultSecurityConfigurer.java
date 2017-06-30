package org.chiwooplatform.security.config;

import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import org.chiwooplatform.security.core.PermissionResolver;
import org.chiwooplatform.security.core.UserPrincipalResolver;
import org.chiwooplatform.security.supports.AnonymousSessionlessAuthenticationFilter;
import org.chiwooplatform.security.supports.RestAuthenticationFailureHandler;
import org.chiwooplatform.security.supports.RestAuthenticationFilter;
import org.chiwooplatform.security.supports.RestAuthenticationProvider;
import org.chiwooplatform.security.supports.RestAuthenticationSuccessHandler;
import org.chiwooplatform.security.supports.TokenPermissionEvaluator;
import org.chiwooplatform.security.web.supports.AntUrisRequestMatcher;
import org.chiwooplatform.security.web.supports.Http403AccessDeniedHandler;
import org.chiwooplatform.security.web.supports.NotRequestMatcher;
import org.chiwooplatform.web.supports.DefaultCorsConfiguration;

public abstract class WebDefaultSecurityConfigurer
    extends WebSecurityConfigurerAdapter {

    public WebDefaultSecurityConfigurer() {
        super( true );
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

    abstract protected String[] excludedAuthenticatedUris();

    abstract protected String authenticatedUri();

    protected Integer maximumSessions() {
        return null;
    }

    protected MediaTypeRequestMatcher jsonMatcher() {
        MediaTypeRequestMatcher matcher = new MediaTypeRequestMatcher( new HeaderContentNegotiationStrategy(),
                                                                       MediaType.APPLICATION_JSON );
        // matcher.setIgnoredMediaTypes( ImmutableSet.of(MediaType.ALL));
        return matcher;
    }

    protected RequestMatcher ajaxMatcher() {
        RequestMatcher matcher = new RequestHeaderRequestMatcher( "X-Requested-With", "XMLHttpRequest" );
        return matcher;
    }

    @Override
    protected void configure( HttpSecurity http )
        throws Exception {
        DefaultCorsConfiguration cors = new DefaultCorsConfiguration();
        // @formatter:off
        http
            .csrf().disable()
            .cors()
                .configurationSource( cors ).and()
            .addFilter(new WebAsyncManagerIntegrationFilter())
            .exceptionHandling()
                .accessDeniedHandler( accessDeniedHandler() )
                .defaultAuthenticationEntryPointFor( aep401(), jsonMatcher() )
                .defaultAuthenticationEntryPointFor( aep401(), ajaxMatcher() )
                .defaultAuthenticationEntryPointFor( aep403(), new NotRequestMatcher( new AntUrisRequestMatcher( excludedAuthenticatedUris()))).and()
            .headers().and()            
            .securityContext().and()
            .requestCache().and()
            // .anonymous().and()
            .servletApi().and()
            .apply(new DefaultLoginPageConfigurer<HttpSecurity>()).and();
        // @formatter:on
        if ( maximumSessions() != null ) {
            http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.IF_REQUIRED )
                .maximumSessions( maximumSessions() );
        } else {
            http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.IF_REQUIRED ).and();
        }
        http.authorizeRequests().antMatchers( authenticatedUri() ).authenticated().anyRequest().permitAll();
        //        
        //        http.formLogin()
        //        .failureForwardUrl( forwardUrl )
        //        .passwordParameter( passwordParameter )
        //        .usernameParameter( usernameParameter )
        //        .loginProcessingUrl( loginProcessingUrl )
        //        .loginPage( loginPage )
        //        .defaultSuccessUrl( defaultSuccessUrl, alwaysUse )
        http.logout().and()
            .addFilterBefore( new AnonymousSessionlessAuthenticationFilter(), SessionManagementFilter.class )
            .addFilterBefore( restFilter(), UsernamePasswordAuthenticationFilter.class );
    }

    protected AuthenticationEntryPoint aep401() {
        return new Http401AuthenticationEntryPoint( "UnAuthorized" );
    }

    protected AuthenticationEntryPoint aep403() {
        return new Http403ForbiddenEntryPoint();
    }

    protected AccessDeniedHandler accessDeniedHandler() {
        return new Http403AccessDeniedHandler();
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
