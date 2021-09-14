package org.xiaoxianyu.common.core.exception;

/**
 * 业务异常
 *
 * @author rorschach
 * @date 2021/9/14 11:05
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 6610083281801529147L;

    public BusinessException(String message) {
        super(message);
    }
}
