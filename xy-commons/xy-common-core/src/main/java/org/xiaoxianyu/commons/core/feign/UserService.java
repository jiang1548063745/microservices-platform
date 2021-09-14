package org.xiaoxianyu.commons.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.xiaoxianyu.commons.core.constant.ServiceNameConstants;
import org.xiaoxianyu.commons.core.domain.SysUser;
import org.xiaoxianyu.commons.core.feign.fallback.UserServiceFallbackFactory;

/**
 * 用户服务接口
 *
 * @author rorschach
 * @date 2021/9/14 11:31
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = UserServiceFallbackFactory.class, decode404 = true)
public interface UserService {

    /**
     * feign rpc访问远程/users/{username}接口
     * 查询用户实体对象SysUser
     *
     * @param username 用户名
     * @return {@link SysUser}
     */
    @GetMapping(value = "/users/name/{username}")
    SysUser selectByUsername(@PathVariable("username") String username);

//    /**
//     * feign rpc访问远程/users-anon/login接口
//     *
//     * @param username 用户名
//     * @return
//     */
//    @GetMapping(value = "/users-anon/login", params = "username")
//    LoginAppUser findByUsername(@RequestParam("username") String username);
//
//    /**
//     * 通过手机号查询用户、角色信息
//     *
//     * @param mobile 手机号
//     */
//    @GetMapping(value = "/users-anon/mobile", params = "mobile")
//    LoginAppUser findByMobile(@RequestParam("mobile") String mobile);
//
//    /**
//     * 根据OpenId查询用户信息
//     *
//     * @param openId openId
//     */
//    @GetMapping(value = "/users-anon/openId", params = "openId")
//    LoginAppUser findByOpenId(@RequestParam("openId") String openId);
}
