package org.xiaoxianyu.common.core.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 锁对象抽象
 *
 * @author rorschach
 * @date 2021/9/14 9:52
 */
@AllArgsConstructor
public class SysLock implements AutoCloseable {

    @Getter
    private final Object lock;

    private final DistributedLock locker;

    @Override
    public void close() {
        locker.unlock(lock);
    }
}
