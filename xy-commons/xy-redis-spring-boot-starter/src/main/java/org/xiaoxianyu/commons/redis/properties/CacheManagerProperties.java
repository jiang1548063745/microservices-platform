package org.xiaoxianyu.commons.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Redis 配置管理类
 *
 * @author rorschach
 * @date 2021/9/14 15:37
 */
@Data
@ConfigurationProperties(prefix = "xy.cache-manager")
public class CacheManagerProperties {

    private List<CacheConfig> configs;

    @Data
    public static class CacheConfig {

        /**
         * cache key
         */
        private String key;

        /**
         * 过期时间，sec
         */
        private long second = 60;
    }
}
