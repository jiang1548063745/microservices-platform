package org.xiaoxianyu.commons.oauth2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Security 配置
 *
 * @author rorschach
 * @date 2021/9/14 16:18
 */
@Data
@ConfigurationProperties(prefix = "xy.security")
@RefreshScope
public class SecurityProperties {

    private AuthProperties auth = new AuthProperties();

    private PermitProperties ignore = new PermitProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();
}
