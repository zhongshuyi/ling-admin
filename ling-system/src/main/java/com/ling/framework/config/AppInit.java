package com.ling.framework.config;

import com.ling.common.annotation.LoginAuth;
import com.ling.framework.util.RequestUtil;
import com.ling.system.entity.SysPermissionUrl;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 应用初始化.
 *
 * @author 钟舒艺
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class AppInit implements CommandLineRunner {

    /**
     * RequestMappingHandlerMapping是在DispatcherServlet的初始化过程中自动加载的，默认会自动加载所有实现HandlerMapping接口的bean.
     */
    private final RequestMappingHandlerMapping mapping;

    /**
     * 请求校验.
     */
    private final RequestUtil requestUtil;

    /**
     * 配置信息.
     */
    private final CustomConfig config;


    @Override
    public final void run(final String... args) {
        initUrl();

    }


    /**
     * 获取 所有URL Mapping，返回格式为{"/test":["GET","POST"],"/sys":["GET","DELETE"]}.
     */
    private void initUrl() {
        // 获取url与类和方法的对应信息
        final Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();

        handlerMethods.forEach((k, v) -> {
            // 获取当前 key 下的获取所有URL
            assert k.getPatternsCondition() != null;
            final Set<String> url = k.getPatternsCondition().getPatterns();
            final RequestMethodsRequestCondition method = k.getMethodsCondition();
            // 忽略的路径
            for (final String u : url) {
                for (final RequestMethod m : method.getMethods()) {
                    if (requestUtil.checkIgnores(m.name(), u)) {
                        config.getIgnoreUrl().add(
                                new SysPermissionUrl(
                                        null,
                                        null,
                                        v.hasMethodAnnotation(ApiOperation.class)
                                                ? Objects.requireNonNull(
                                                v.getMethodAnnotation(ApiOperation.class)).value()
                                                : "",
                                        u,
                                        m.name()));
                        return;
                    }
                }
            }
            // 有LoginAuth注解的是登录后就能访问的路径
            if (AnnotatedElementUtils.hasAnnotation(v.getBeanType(), LoginAuth.class)
                    ||
                    v.hasMethodAnnotation(LoginAuth.class)
            ) {
                for (final String u : url) {
                    for (final RequestMethod m : method.getMethods()) {
                        config.getLoginHasAuth().add(
                                new SysPermissionUrl(
                                        null,
                                        null,
                                        v.hasMethodAnnotation(ApiOperation.class)
                                                ? Objects.requireNonNull(
                                                v.getMethodAnnotation(ApiOperation.class)).value()
                                                : "",
                                        u,
                                        m.name()
                                ));
                    }
                }
            } else {
                // 其他的就是需要鉴权的路径了
                for (final String u : url) {
                    for (final RequestMethod m : method.getMethods()) {
                        config.getPermUrl().add(
                                new SysPermissionUrl(
                                        null,
                                        null,
                                        v.hasMethodAnnotation(ApiOperation.class)
                                                ? Objects.requireNonNull(
                                                v.getMethodAnnotation(ApiOperation.class)).value()
                                                : "",
                                        u,
                                        m.name()
                                ));
                    }
                }
            }
        });
    }
}
