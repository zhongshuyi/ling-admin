package com.mall.framework.config;

import com.mall.framework.security.filter.JwtAuthenticationTokenFilter;
import com.mall.framework.security.handle.RestAuthenticationEntryPoint;
import com.mall.framework.security.handle.RestfulAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity的配置类
 * 文档记录: https://www.yuque.com/rookieteam/bvc9h3/gbvtga#g5MxD
 * Spring Security默认是禁用注解的,要想开启注解需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解
 * 判断用户对某个控制层的方法是否具有访问权限
 *
 * @author 钟舒艺
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 登录用户服务类.
     */
    private final UserDetailsService userDetailsService;

    /**
     * 认证失败处理类 返回未授权.
     */
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    /**
     * 当未登录或者token失效访问接口时，自定义的返回结果.
     */
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * token认证过滤器.
     */
    private final JwtAuthenticationTokenFilter authenticationTokenFilter;


    /**
     * 构造方法.
     *
     * @param restfulAccessDeniedHandler   认证失败处理类
     * @param restAuthenticationEntryPoint 当未登录或者token失效访问接口时，自定义的返回
     * @param userDetailsService           登录用户服务类
     * @param authenticationTokenFilter    token认证过滤器
     */
    public SecurityConfig(
            final RestfulAccessDeniedHandler restfulAccessDeniedHandler,
            final RestAuthenticationEntryPoint restAuthenticationEntryPoint,
            final UserDetailsService userDetailsService,
            final JwtAuthenticationTokenFilter authenticationTokenFilter) {
        this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.authenticationTokenFilter = authenticationTokenFilter;
    }


    /**
     * 用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器.
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     *
     * @param httpSecurity 配置
     * @throws Exception 异常
     */
    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        // CSRF禁用，因为不使用session
        httpSecurity
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login  允许匿名访问
                .antMatchers("/login").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/favicon.ico"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/swagger-ui/**").anonymous()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/druid/**").anonymous()
                .antMatchers("/ws/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                // 将安全标头添加到响应
                .headers()
                // 允许 x-frame-options deny iframe调用
                .frameOptions().disable()
                // 禁用缓存
                .cacheControl();

        // 添加JWT过滤器
        httpSecurity.addFilterBefore(
                authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                // 未授权
                .accessDeniedHandler(restfulAccessDeniedHandler)
                // 未登录
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected final void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(cryptPasswordEncoder());
    }

    /**
     * 强散列哈希加密实现.
     *
     * @return 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取身份验证管理器Bean.
     *
     * @return AuthenticationManager
     * @throws Exception bean获取异常
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
