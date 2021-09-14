package org.xiaoxianyu.commons.oauth2;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.xiaoxianyu.commons.oauth2.properties.SecurityProperties;
import org.xiaoxianyu.commons.oauth2.properties.TokenStoreProperties;

/**
 * 鉴权自动配置
 *
 * @author rorschach
 * @date 2021/9/14 15:52
 */
@EnableConfigurationProperties({SecurityProperties.class, TokenStoreProperties.class})
@ComponentScan
public class AuthClientAutoConfiguration {
}
