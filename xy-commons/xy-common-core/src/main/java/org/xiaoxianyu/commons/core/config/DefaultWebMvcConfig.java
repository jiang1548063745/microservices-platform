package org.xiaoxianyu.commons.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xiaoxianyu.commons.core.feign.UserService;
import org.xiaoxianyu.commons.core.resolver.ClientArgumentResolver;
import org.xiaoxianyu.commons.core.resolver.TokenArgumentResolver;

import java.util.List;

/**
 * 默认SpringMVC拦截器
 *
 * @author rorschach
 * @date 2021/9/14 9:40
 */
public class DefaultWebMvcConfig implements WebMvcConfigurer {

    @Lazy
    @Autowired
    private UserService userService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 注入用户信息
        resolvers.add(new TokenArgumentResolver(userService));
        // 注入应用信息
        resolvers.add(new ClientArgumentResolver());
    }
}
