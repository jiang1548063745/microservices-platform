package org.xiaoxianyu.commons.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 *
 * @author rorschach
 * @date 2021/9/14 15:11
 */
@Data
@ConfigurationProperties(prefix = "xy.tenant")
@RefreshScope
public class TenantProperties {

    /**
     * 是否开启多租户
     */
    private Boolean enable = false;

    /**
     * 配置不进行多租户隔离的表名
     */
    private List<String> ignoreTables = new ArrayList<>();

    /**
     * 配置不进行多租户隔离的sql
     * 需要配置mapper的全路径如：com.central.user.mapper.SysUserMapper.findList
     */
    private List<String> ignoreSqls = new ArrayList<>();
}
