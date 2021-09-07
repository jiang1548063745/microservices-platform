package org.xiaoxianyu.common.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 日志链路追踪配置
 *
 * @author rorschach
 * @date 2021/9/7 10:42
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "xy.trace")
public class TraceProperties {

    /**
     * 是否开启日志链路追踪
     */
    private Boolean enable = false;
}
