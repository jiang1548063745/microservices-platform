package org.xiaoxianyu.commons.oauth2.store;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.util.ClassUtils;
import org.xiaoxianyu.commons.oauth2.properties.SecurityProperties;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 优化自Spring Security的RedisTokenStore
 * 1. 支持redis所有集群模式包括cluster模式
 * 2. 使用pipeline减少连接次数，提升性能
 * 3. 自动续签token（可配置是否开启）
 *
 * @author rorschach
 * @date 2021/9/14 16:37
 */
public class CustomRedisTokenStore implements TokenStore {

    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";

    private static final boolean springDataRedis_2_0 = ClassUtils.isPresent(
            "org.springframework.data.redis.connection.RedisStandaloneConfiguration",
            RedisTokenStore.class.getClassLoader());

    private final RedisConnectionFactory connectionFactory;
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    private String prefix = "";

    private Method redisConnectionSet_2_0;

    public CustomRedisTokenStore(RedisConnectionFactory connectionFactory, SecurityProperties securityProperties, RedisSerializer<Object> redisValueSerializer) {
        this.connectionFactory = connectionFactory;
        this.securityProperties = securityProperties;
        this.redisValueSerializer = redisValueSerializer;
        if (springDataRedis_2_0) {
//            this.loadRedisConnectionMethods_2_0();
        }
    }

    /**
     * 认证配置
     */
    private SecurityProperties securityProperties;

    /**
     * 业务redis的value序列化
     */
    private RedisSerializer<Object> redisValueSerializer;

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {

    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {

    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return null;
    }
}
