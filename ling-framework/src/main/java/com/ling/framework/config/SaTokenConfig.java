package com.ling.framework.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 配置.
 *
 * @author 钟舒艺
 * @since 2022-10-22 20:16
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 全局配置.
     */
    private final CustomConfig customConfig;

    /**
     * 注册拦截器.
     *
     * @param registry 注册信息.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 放行配置文件中忽略的路径
            releaseIgnores();
            // 其余全部拦截并检查
            SaRouter.match("/**").check(r -> {
                StpUtil.checkLogin();
            });
        }));


    }


    /**
     * 放行忽略的路径.
     */
    private void releaseIgnores() {
        // 不考虑请求方法需要忽略的路径
        SaRouter.match(customConfig.getIgnores().getPattern()).stop();
        // 按照方法与路径匹配
        SaRouter.match(SaHttpMethod.GET).match(customConfig.getIgnores().getGet()).stop();
        SaRouter.match(SaHttpMethod.POST).match(customConfig.getIgnores().getPost()).stop();
        SaRouter.match(SaHttpMethod.DELETE).match(customConfig.getIgnores().getDelete()).stop();
        SaRouter.match(SaHttpMethod.PUT).match(customConfig.getIgnores().getPut()).stop();
        SaRouter.match(SaHttpMethod.HEAD).match(customConfig.getIgnores().getHead()).stop();
        SaRouter.match(SaHttpMethod.PATCH).match(customConfig.getIgnores().getPatch()).stop();
        SaRouter.match(SaHttpMethod.PATCH).match(customConfig.getIgnores().getPatch()).stop();
        SaRouter.match(SaHttpMethod.OPTIONS).match(customConfig.getIgnores().getOptions()).stop();
        SaRouter.match(SaHttpMethod.TRACE).match(customConfig.getIgnores().getTrace()).stop();
        SaRouter.match(SaHttpMethod.CONNECT).match(customConfig.getIgnores().getConnect()).stop();
    }

}


