package com.mall.framework.config;

import com.google.common.collect.ArrayListMultimap;
import com.mall.common.annotation.LoginAuth;
import com.mall.system.entity.UmsPermissionUrl;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
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
     * 配置信息.
     */
    private final CustomConfig config;


    @Override
    public final void run(final String... args) throws Exception {
        config.setLoginHasAuth(new ArrayList<>());
        config.setAllUrl(allUrlMapping());

    }


    /**
     * 获取 所有URL Mapping，返回格式为{"/test":["GET","POST"],"/sys":["GET","DELETE"]}.
     *
     * @return {@link ArrayListMultimap} 格式的 URL Mapping
     */
    private List<UmsPermissionUrl> allUrlMapping() {
        // 获取url与类和方法的对应信息
        final Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();

        final List<UmsPermissionUrl> allUrlList = new ArrayList<>();

        handlerMethods.forEach((k, v) -> {
            // 获取当前 key 下的获取所有URL
            assert k.getPatternsCondition() != null;
            final Set<String> url = k.getPatternsCondition().getPatterns();
            final RequestMethodsRequestCondition method = k.getMethodsCondition();
            // 有LoginAuth注解的是登录后就能访问的路径
            if (AnnotatedElementUtils.hasAnnotation(v.getBeanType(), LoginAuth.class) || v.hasMethodAnnotation(
                    LoginAuth.class)) {
                url.forEach(
                        s -> method.getMethods().forEach(
                                m -> {
                                    if (v.hasMethodAnnotation(ApiOperation.class)) {
                                        config.getLoginHasAuth().add(
                                                new UmsPermissionUrl(
                                                        null,
                                                        null,
                                                        Objects.requireNonNull(
                                                                v.getMethodAnnotation(ApiOperation.class)).value(),
                                                        s,
                                                        m.name()
                                                ));
                                    } else {
                                        config.getLoginHasAuth().add(
                                                new UmsPermissionUrl(null, null, "", s, m.name()));
                                    }
                                }
                        )
                );
            }
            url.forEach(u -> method.getMethods().forEach(m -> {
                if (v.hasMethodAnnotation(ApiOperation.class)) {
                    allUrlList.add(
                            new UmsPermissionUrl(
                                    null,
                                    null,
                                    Objects.requireNonNull(v.getMethodAnnotation(ApiOperation.class)).value(),
                                    u,
                                    m.name()
                            ));
                } else {
                    allUrlList.add(new UmsPermissionUrl(null, null, "", u, m.name()));
                }
            }));
        });
        return allUrlList;
    }
}
