package org.xiaoxianyu.commons.oauth2.store;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器使用数据库存取令牌
 *
 * @author rorschach
 * @date 2021/9/14 16:35
 */
@Configuration
@ConditionalOnProperty(prefix = "xy.oauth2.token.store", name = "type", havingValue = "db")
public class AuthDbTokenStore {

    private final DataSource dataSource;

    public AuthDbTokenStore(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }
}
