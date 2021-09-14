package org.xiaoxianyu.commons.oauth2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Token配置
 *
 * @author rorschach
 * @date 2021/9/14 16:19
 */
@Data
@ConfigurationProperties(prefix = "xy.oauth2.token.store")
@RefreshScope
public class TokenStoreProperties {

    /**
     * token存储类型(redis/db/authJwt/resJwt)
     */
    private String type = "redis";
}
