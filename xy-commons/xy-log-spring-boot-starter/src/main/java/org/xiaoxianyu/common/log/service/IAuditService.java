package org.xiaoxianyu.common.log.service;

import org.xiaoxianyu.common.log.model.Audit;

/**
 * 审计日志服务接口
 *
 * @author rorschach
 * @date 2021/9/7 11:00
 */
public interface IAuditService {

    /**
     * 保存审计日志
     *
     * @param audit {@link Audit}
     * @return 保存回执
     */
    boolean save(Audit audit);
}
