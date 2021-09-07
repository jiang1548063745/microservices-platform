package org.xiaoxianyu.common.log.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志数据源配置
 *
 * @author rorschach
 * @date 2021/9/7 10:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "xy.audit-log.datasource")
public class LogDbProperties extends HikariConfig {
}
