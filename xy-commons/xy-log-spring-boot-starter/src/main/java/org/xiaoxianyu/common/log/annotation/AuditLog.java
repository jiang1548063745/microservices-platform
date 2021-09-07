package org.xiaoxianyu.common.log.annotation;

import java.lang.annotation.*;

/**
 * 审计日志注解
 *
 * @author rorschach
 * @date 2021/9/7 10:52
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * 操作信息
     */
    String operation();
}
