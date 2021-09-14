package org.xiaoxianyu.commons.core.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.xiaoxianyu.commons.core.domain.SysUser;
import org.xiaoxianyu.commons.core.feign.UserService;

/**
 * @author rorschach
 * @date 2021/9/14 15:05
 */
@Slf4j
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {

    @Override
    public UserService create(Throwable cause) {
        return new UserService() {
            @Override
            public SysUser selectByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, cause);
                return new SysUser();
            }

            // TODO
        };
    }
}
