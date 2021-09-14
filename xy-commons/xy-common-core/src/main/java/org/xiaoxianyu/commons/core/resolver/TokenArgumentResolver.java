package org.xiaoxianyu.commons.core.resolver;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.xiaoxianyu.commons.core.annotation.LoginUser;
import org.xiaoxianyu.commons.core.constant.SecurityConstants;
import org.xiaoxianyu.commons.core.domain.SysUser;
import org.xiaoxianyu.commons.core.feign.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Token转化SysUser
 *
 * @author rorschach
 * @date 2021/9/14 11:52
 */
@Slf4j
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    public TokenArgumentResolver(UserService userService) {
        this.userService = userService;
    }

    /**
     * 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().equals(SysUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);

        if (null == loginUser) {
            log.warn("resolveArgument error loginUser is empty");
            return null;
        }

        boolean isFull = loginUser.isFull();

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (null == request) {
            log.warn("resolveArgument error request is empty");
            return null;
        }

        String userId = request.getHeader(SecurityConstants.USER_ID_HEADER);
        String username = request.getHeader(SecurityConstants.USER_HEADER);
//        String roles = request.getHeader(SecurityConstants.ROLE_HEADER);

        //账号类型
//        String accountType = request.getHeader(SecurityConstants.ACCOUNT_TYPE_HEADER);

        if (StrUtil.isBlank(username)) {
            log.warn("resolveArgument error username is empty");
            return null;
        }

        SysUser user;
        if (isFull) {
            // TODO
//            user = userService.selectByUsername(username);
            user = new SysUser();
        } else {
            user = new SysUser();
            user.setId(Long.valueOf(userId));
            user.setUsername(username);
        }

//        List<SysRole> sysRoleList = new ArrayList<>();
//        Arrays.stream(roles.split(",")).forEach(role -> {
//            SysRole sysRole = new SysRole();
//            sysRole.setCode(role);
//            sysRoleList.add(sysRole);
//        });
//        user.setRoles(sysRoleList);

        return user;
    }
}
