package com.ling.framework.config;

import com.ling.framework.security.filter.JwtAuthenticationTokenFilter;
import com.ling.framework.security.handle.RestAuthenticationEntryPoint;
import com.ling.framework.security.handle.RestfulAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity的配置类.
 * 文档记录:
 * <a href="https://www.yuque.com/rookieteam/bvc9h3/gbvtga#g5MxD">https://www.yuque.com/rookieteam/bvc9h3/gbvtga#g5MxD</a>
 * Spring Security默认是禁用注解的,要想开启注解需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解
 * 判断用户对某个控制层的方法是否具有访问权限
 *
 * @author 钟舒艺
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置信息.
     */
    private final CustomConfig customConfig;

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
        httpSecurity.cors()
                // 关闭 CSRF
                .and().csrf().disable()
                // 登录行为由自己实现
                .formLogin().disable()
                .httpBasic().disable()

                // 认证请求
                .authorizeRequests()
                // RBAC 动态 url 认证
                .anyRequest().access("@rbacAuthorityService.hasPermission(request,authentication)")
                // 登出行为由自己实现
                .and().logout().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 将安全标头添加到响应
                .and().headers()
                // 允许 x-frame-options deny iframe调用
                .frameOptions().disable()
                // 禁用缓存
                .cacheControl()
        ;

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

    @Override
    public final void configure(final WebSecurity web) {
        final WebSecurity and = web.ignoring().and();

        // 忽略 GET
        customConfig.getIgnores().getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));

        // 忽略 POST
        customConfig.getIgnores().getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));

        // 忽略 DELETE
        customConfig.getIgnores().getDelete().forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url));

        // 忽略 PUT
        customConfig.getIgnores().getPut().forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url));

        // 忽略 HEAD
        customConfig.getIgnores().getHead().forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url));

        // 忽略 PATCH
        customConfig.getIgnores().getPatch().forEach(url -> and.ignoring().antMatchers(HttpMethod.PATCH, url));

        // 忽略 OPTIONS
        customConfig.getIgnores().getOptions().forEach(url -> and.ignoring().antMatchers(HttpMethod.OPTIONS, url));

        // 忽略 TRACE
        customConfig.getIgnores().getTrace().forEach(url -> and.ignoring().antMatchers(HttpMethod.TRACE, url));

        // 按照请求格式忽略
        customConfig.getIgnores().getPattern().forEach(url -> and.ignoring().antMatchers(url));
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
