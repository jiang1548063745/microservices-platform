package org.xiaoxianyu.commons.core.resolver;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.xiaoxianyu.commons.core.annotation.LoginClient;
import org.xiaoxianyu.commons.core.constant.SecurityConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * head中的应用参数注入clientId中
 *
 * @author rorschach
 * @date 2021/9/14 11:35
 */
@Slf4j
public class ClientArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 入参筛选
     *
     * @param methodParameter  参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginClient.class) && methodParameter.getParameterType().equals(String.class);
    }

    /**
     * 解析需要的参数
     *
     * @param methodParameter        入参集合
     * @param modelAndViewContainer  model 和 view
     * @param nativeWebRequest       web相关
     * @param webDataBinderFactory   入参解析
     * @return    包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (null == request) {
            log.warn("resolveArgument error request is empty");
            return null;
        }

        String clientId = request.getHeader(SecurityConstants.TENANT_HEADER);
        if (StrUtil.isBlank(clientId)) {
            log.warn("resolveArgument error clientId is empty");
        }
        return clientId;
    }
}
