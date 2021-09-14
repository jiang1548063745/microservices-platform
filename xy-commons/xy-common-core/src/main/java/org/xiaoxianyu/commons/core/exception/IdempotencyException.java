package org.xiaoxianyu.commons.core.exception;

/**
 * 幂等性异常
 *
 * @author rorschach
 * @date 2021/9/14 10:27
 */
public class IdempotencyException extends RuntimeException {
    private static final long serialVersionUID = 6610083281801529147L;

    public IdempotencyException(String message) {
        super(message);
    }
}
