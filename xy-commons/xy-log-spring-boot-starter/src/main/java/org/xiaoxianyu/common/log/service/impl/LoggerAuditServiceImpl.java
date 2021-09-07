package org.xiaoxianyu.common.log.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.xiaoxianyu.common.log.model.Audit;
import org.xiaoxianyu.common.log.service.IAuditService;

import java.time.format.DateTimeFormatter;

/**
 * 审计日志实现类-打印日志
 *
 * @author rorschach
 * @date 2021/9/7 11:05
 */
@Slf4j
@ConditionalOnProperty(name = "xy.audit-log.log-type", havingValue = "logger", matchIfMissing = true)
public class LoggerAuditServiceImpl implements IAuditService {

    /**
     * 消息格式 格式为：{时间}|{应用名}|{类名}|{方法名}|{用户id}|{用户名}|{租户id}|{操作信息}
     *
     * 例子：2020-02-04 09:13:34.650|user-center|com.central.user.controller.SysUserController|saveOrUpdate|1|admin|webApp|新增用户:admin
     */
    private static final String MSG_PATTERN = "{}|{}|{}|{}|{}|{}|{}|{}";

    @Override
    public boolean save(Audit audit) {
        log.debug(
                MSG_PATTERN,
                audit.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                audit.getApplicationName(),
                audit.getClassName(),
                audit.getMethodName(),
                audit.getUserId(),
                audit.getUserName(),
                audit.getClientId(),
                audit.getOperation());
        return true;
    }
}
