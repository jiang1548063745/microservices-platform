package org.xiaoxianyu.commons.core.annotation;

import java.lang.annotation.*;

/**
 * 请求的方法参数SysUser上添加该注解，则注入当前登录人信息
 * 例1：public void test(@LoginUser SysUser user) //只有username 和 roles
 * 例2：public void test(@LoginUser(isFull = true) SysUser user) //能获取SysUser对象的所有信息
 *
 * @author rorschach
 * @date 2021/9/7 16:24
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {

    /**
     * 是否查询SysUser对象所有信息，true则通过rpc接口查询
     */
    boolean isFull() default false;
}
