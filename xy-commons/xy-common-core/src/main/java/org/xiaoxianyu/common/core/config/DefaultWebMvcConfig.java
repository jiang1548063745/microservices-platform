package org.xiaoxianyu.common.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xiaoxianyu.common.core.feign.UserService;
import org.xiaoxianyu.common.core.resolver.ClientArgumentResolver;
import org.xiaoxianyu.common.core.resolver.TokenArgumentResolver;

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
        resolvers.add(new TokenArgumentResolver(userService));
        //注入应用信息
        resolvers.add(new ClientArgumentResolver());
    }
}
