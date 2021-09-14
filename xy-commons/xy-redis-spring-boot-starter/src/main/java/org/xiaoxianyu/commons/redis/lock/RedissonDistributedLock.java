package org.xiaoxianyu.commons.redis.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.xiaoxianyu.commons.core.constant.CommonConstant;
import org.xiaoxianyu.commons.core.exception.LockException;
import org.xiaoxianyu.commons.core.lock.DistributedLock;
import org.xiaoxianyu.commons.core.lock.SysLock;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁实现，基本锁功能的抽象实现
 * 本接口能满足绝大部分的需求，高级的锁功能，请自行扩展或直接使用原生api
 *
 * @author rorschach
 * @date 2021/9/14 15:33
 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "xy.lock", name = "lockerType", havingValue = "REDIS", matchIfMissing = true)
public class RedissonDistributedLock implements DistributedLock {

    private final RedissonClient redisson;

    public RedissonDistributedLock(RedissonClient redisson) {
        this.redisson = redisson;
    }

    private SysLock getLock(String key, boolean isFair) {
        RLock lock;
        if (isFair) {
            lock = redisson.getFairLock(CommonConstant.LOCK_KEY_PREFIX + ":" + key);
        } else {
            lock =  redisson.getLock(CommonConstant.LOCK_KEY_PREFIX + ":" + key);
        }
        return new SysLock(lock, this);
    }

    @Override
    public SysLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) {
        SysLock sysLock = getLock(key, isFair);
        RLock lock = (RLock)sysLock.getLock();
        lock.lock(leaseTime, unit);
        return sysLock;
    }

    @Override
    public SysLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        SysLock sysLock = getLock(key, isFair);
        RLock lock = (RLock)sysLock.getLock();
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return sysLock;
        }
        return null;
    }

    @Override
    public void unlock(Object lock) {
        if (lock != null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock)lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
