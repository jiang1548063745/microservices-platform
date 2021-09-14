package org.xiaoxianyu.commons.core.exception;

/**
 * 分布式锁异常
 *
 * @author rorschach
 * @date 2021/9/14 10:21
 */
public class LockException extends RuntimeException {

    private static final long serialVersionUID = 6610083281801529147L;

    public LockException(String message) {
        super(message);
    }
}
