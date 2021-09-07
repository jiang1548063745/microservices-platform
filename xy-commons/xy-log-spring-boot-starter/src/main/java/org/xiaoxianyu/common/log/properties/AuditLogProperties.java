package org.xiaoxianyu.common.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 审计日志配置
 *
 * @author rorschach
 * @date 2021/9/7 10:47
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "xy.audit-log")
public class AuditLogProperties {

    /**
     * 是否开启审计日志
     */
    private Boolean enabled = false;

    /**
     * 日志记录类型(logger/redis/db/es)
     */
    private String logType;
}
