package org.xiaoxianyu.commons.oauth2.properties;

import lombok.Data;

/**
 * 验证码配置
 *
 * @author rorschach
 * @date 2021/9/14 16:18
 */
@Data
public class ValidateCodeProperties {

    /**
     * 设置认证通时不需要验证码的clientId
     */
    private String[] ignoreClientCode = {};
}
