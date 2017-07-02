package org.chiwooplatform.security.config;

import java.util.stream.Stream;

import org.chiwooplatform.security.core.PermissionResolver;
import org.chiwooplatform.security.core.UserPrincipalResolver;
import org.chiwooplatform.security.supports.RestAuthenticationProvider;
import org.chiwooplatform.security.supports.TokenPermissionEvaluator;
import org.chiwooplatform.security.web.supports.Http403AccessDeniedHandler;
import org.chiwooplatform.web.supports.DefaultCorsConfiguration;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

public abstract class WebDefaultSecurityConfigurer
    extends WebSecurityConfigurerAdapter
{
    public WebDefaultSecurityConfigurer()
    {
        super( false );
    }

    private static final String[] EXCLUDED_WEB_STATIC_RESOURCES = new String[] {
        "/",
        "/static/**",
        "/assets/**",
        "/resources/**",
        "/favicon.ico",
        "/css/**",
        "/js/**" };

    abstract protected String[] staticUriPatterns();

    private String[] ignoringPaths()
    {
        String[] uris = staticUriPatterns();
        if ( uris == null )
        {
            return EXCLUDED_WEB_STATIC_RESOURCES;
        }
        return uris;
    }

    private static final String[] PERMITALL_URI_PATTERNS = new String[] {
        "/login",
        "/logout",
        "/register",
        "/signup",
        "/signin" };

    abstract protected String[] permitAllUriPatterns();

    protected String loginUrl()
    {
        return "/login";
    }

    private String[] permitAllPaths()
    {
        String[] uris = permitAllUriPatterns();
        if ( uris == null )
        {
            return PERMITALL_URI_PATTERNS;
        }
        return Stream.of( PERMITALL_URI_PATTERNS, uris ).flatMap( Stream::of ).toArray( String[]::new );
    }

    @Override
    public void configure( WebSecurity web )
        throws Exception
    {
        web.ignoring().antMatchers( HttpMethod.OPTIONS ).antMatchers( ignoringPaths() );
        // Apply-EL TO JSP-VIEW
        // DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        // handler.setPermissionEvaluator( permissionEvaluator() );
        // web.expressionHandler( handler );
    }

    protected Integer maximumSessions()
    {
        return null;
    }

    protected MediaTypeRequestMatcher jsonMatcher()
    {
        MediaTypeRequestMatcher matcher = new MediaTypeRequestMatcher( new HeaderContentNegotiationStrategy(),
                                                                       MediaType.APPLICATION_JSON );
        // matcher.setIgnoredMediaTypes( ImmutableSet.of(MediaType.ALL));
        return matcher;
    }

    protected RequestMatcher ajaxMatcher()
    {
        RequestMatcher matcher = new RequestHeaderRequestMatcher( "X-Requested-With", "XMLHttpRequest" );
        return matcher;
    }

    @Override
    protected void configure( HttpSecurity http )
        throws Exception
    {
        DefaultCorsConfiguration cors = new DefaultCorsConfiguration();
        // @formatter:off
        http.csrf().disable()
            .cors().configurationSource( cors ).and()
            .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.IF_REQUIRED )
//            .addFilter( new WebAsyncManagerIntegrationFilter() )
//            .exceptionHandling()
//            .accessDeniedHandler( accessDeniedHandler() )
//                .defaultAuthenticationEntryPointFor( aep401(), jsonMatcher() )
//                .defaultAuthenticationEntryPointFor( aep401(), ajaxMatcher() )
//                .defaultAuthenticationEntryPointFor( aep403(),
//                                                 new NotRequestMatcher( new AntUrisRequestMatcher( permitAllPaths() ) ) ).and()
//            .headers().and()
//            .securityContext().and()
//            .requestCache().and()
//           .anonymous().and()
//            .servletApi().and()
//            .apply( new DefaultLoginPageConfigurer<HttpSecurity>() ).and()
            ;
        // @formatter:on
        if ( maximumSessions() != null )
        {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            SessionManagementConfigurer sessionManagement = http.getConfigurer( SessionManagementConfigurer.class );
            sessionManagement.maximumSessions( maximumSessions() );
        }
        // @formatter:off
        http.rememberMe().and()
            .securityContext().and()
            .authorizeRequests()
                .antMatchers( HttpMethod.OPTIONS ).permitAll()
                .antMatchers( permitAllPaths() ).permitAll()
                .anyRequest().authenticated();

         
        http.formLogin()
                .loginPage(loginUrl() )
                .usernameParameter( "userid" )
                .passwordParameter( "password" )
                .failureForwardUrl( loginUrl() + "?error=true")
                // .loginProcessingUrl( "/j_spring_security_check" )
                ;
        
        http.logout()
            .logoutRequestMatcher( new AntPathRequestMatcher( "/logout**" ) )
            .logoutSuccessUrl( "/login" )
            .deleteCookies( "JSESSIONID", "SESSIONID" );
        
        http
            // .addFilterBefore( new AnonymousSessionlessAuthenticationFilter(), SessionManagementFilter.class )
            // .addFilterBefore( restFilter(), UsernamePasswordAuthenticationFilter.class )
        .httpBasic()
            ;
        // @formatter:on
    }

    protected AuthenticationEntryPoint aep401()
    {
        return new Http401AuthenticationEntryPoint( "UnAuthorized" );
    }

    protected AuthenticationEntryPoint aep403()
    {
        return new Http403ForbiddenEntryPoint();
    }

    protected AccessDeniedHandler accessDeniedHandler()
    {
        return new Http403AccessDeniedHandler();
    }

    @Bean
    abstract public UserPrincipalResolver userPrincipalResolver();

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        final RestAuthenticationProvider authenticationProvider = new RestAuthenticationProvider( userPrincipalResolver() );
        return authenticationProvider;
    }

    @Bean
    abstract public PermissionResolver permissionResolver();

    @Bean
    public TokenPermissionEvaluator permissionEvaluator()
    {
        final TokenPermissionEvaluator permissionEvaluator = new TokenPermissionEvaluator( permissionResolver() );
        return permissionEvaluator;
    }
}
