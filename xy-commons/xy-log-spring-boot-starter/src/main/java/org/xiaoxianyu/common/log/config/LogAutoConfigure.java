package org.xiaoxianyu.common.log.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.xiaoxianyu.common.log.properties.AuditLogProperties;
import org.xiaoxianyu.common.log.properties.LogDbProperties;
import org.xiaoxianyu.common.log.properties.TraceProperties;

/**
 * 日志自动配置
 *
 * @author rorschach
 * @date 2021/9/7 10:41
 */
@EnableConfigurationProperties({TraceProperties.class, AuditLogProperties.class})
public class LogAutoConfigure {

    /**
     * 日志数据库配置
     */
    @Configuration
    @ConditionalOnClass(HikariConfig.class)
    @EnableConfigurationProperties(LogDbProperties.class)
    public static class LogDbAutoConfigure {}
}
