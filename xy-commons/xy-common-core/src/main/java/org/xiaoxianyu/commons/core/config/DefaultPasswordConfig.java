package org.xiaoxianyu.commons.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.xiaoxianyu.commons.core.utils.PwdEncoderUtil;

/**
 * 密码加密配置类
 *
 * @author rorschach
 * @date 2021/9/14 9:32
 */
public class DefaultPasswordConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PwdEncoderUtil.getDelegatingPasswordEncoder("bcrypt");
    }
}
