package org.xiaoxianyu.commons.oauth2.store;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.xiaoxianyu.commons.oauth2.properties.SecurityProperties;

/**
 * 认证服务器使用Redis存取令牌
 * 注意: 需要配置redis参数
 *
 * @author rorschach
 * @date 2021/9/14 16:36
 */
@Configuration
@ConditionalOnProperty(prefix = "xy.oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
public class AuthRedisTokenStore {

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory, SecurityProperties securityProperties, RedisSerializer<Object> redisValueSerializer) {
        return new CustomRedisTokenStore(connectionFactory, securityProperties, redisValueSerializer);
    }
}
