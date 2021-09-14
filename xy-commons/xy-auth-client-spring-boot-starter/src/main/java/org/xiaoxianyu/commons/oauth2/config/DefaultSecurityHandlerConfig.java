package org.xiaoxianyu.commons.oauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.xiaoxianyu.commons.core.utils.ResponseUtil;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权处理配置
 *
 * @author rorschach
 * @date 2021/9/14 15:57
 */
public class DefaultSecurityHandlerConfig {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 未登录，返回401
     *
     * @return {@link AuthenticationEntryPoint}
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> ResponseUtil.responseFailed(objectMapper, response, authException.getMessage());
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    /**
     * 处理spring security oauth 处理失败返回消息格式
     */
    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException {
                ResponseUtil.responseFailed(objectMapper, response, authException.getMessage());
            }
        };
    }
}
