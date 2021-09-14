package org.xiaoxianyu.commons.oauth2.constants;

/**
 * 权限常量
 *
 * @author rorschach
 * @date 2021/9/14 16:25
 */
public enum AuthoritiesEnum {

    /**
     * 管理员
     */
    ADMIN("ROLE_ADMIN"),

    /**
     * 普通用户
     */
    USER("ROLE_USER"),

    /**
     * 匿名用户
     */
    ANONYMOUS("ROLE_ANONYMOUS");

    private final String role;

    AuthoritiesEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
