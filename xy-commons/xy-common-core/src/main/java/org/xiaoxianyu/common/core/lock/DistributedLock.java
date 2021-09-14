package org.xiaoxianyu.common.core.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁顶级接口
 *
 * @author rorschach
 * @date 2021/9/14 9:52
 */
public interface DistributedLock {

    /**
     * 获取锁, 如果获取不成功则一直等待直到lock被获取
     *
     * @param key       锁的key
     * @param leaseTime 加锁的时间，超过这个时间后锁便自动解锁；
     *                  如果leaseTime为-1，则保持锁定直到显式解锁
     * @param unit      {@link TimeUnit} 参数的时间单位
     * @param isFair    是否公平锁
     * @return          锁对象
     */
    SysLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair);

    /**
     * 默认获取锁方法
     *
     * @param key       锁的key
     * @param leaseTime 加锁的时间，超过这个时间后锁便自动解锁；
     *                  如果leaseTime为-1，则保持锁定直到显式解锁
     * @param unit      {@link TimeUnit} 参数的时间单位
     * @return          锁对象
     */
    default SysLock lock(String key, long leaseTime, TimeUnit unit) {
        return this.lock(key, leaseTime, unit, false);
    }

    /**
     * 默认获取锁方法
     *
     * @param key        锁的key
     * @param isFair     是否公平锁
     * @return           锁对象
     */
    default SysLock lock(String key, boolean isFair) {
        return this.lock(key, -1, null, isFair);
    }

    /**
     * 默认获取锁方法
     *
     * @param key        锁的key
     * @return           锁对象
     */
    default SysLock lock(String key) {
        return this.lock(key, -1, null, false);
    }

    /**
     * 尝试获取锁，如果锁不可用则等待最多waitTime时间后放弃
     *
     * @param key        锁的key
     * @param waitTime   获取锁的最大尝试时间
     * @param leaseTime  加锁的时间，超过这个时间后锁便自动解锁；
     *                   如果leaseTime为-1，则保持锁定直到显式解锁
     * @param unit       {@link TimeUnit} 参数的时间单位
     * @param isFair     是否公平锁
     * @return           锁对象，如果获取锁失败则为null
     */
    SysLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair);

    /**
     * 尝试获取锁，如果锁不可用则等待最多waitTime时间后放弃(非公平)
     *
     * @param key        锁的key
     * @param waitTime   获取锁的最大尝试时间
     * @param leaseTime  加锁的时间，超过这个时间后锁便自动解锁；
     * @param unit       {@link TimeUnit} 参数的时间单位
     * @return           锁对象，如果获取锁失败则为null
     */
    default SysLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        return this.tryLock(key, waitTime, leaseTime, unit, false);
    }

    /**
     * 尝试获取锁，如果锁不可用则等待最多waitTime时间后放弃(需要手动释放)
     *
     * @param key        锁的key
     * @param waitTime   获取锁的最大尝试时间
     * @param unit       加锁的时间，超过这个时间后锁便自动解锁；
     * @param isFair     是否公平锁
     * @return           锁对象，如果获取锁失败则为null
     */
    default SysLock tryLock(String key, long waitTime, TimeUnit unit, boolean isFair) {
        return this.tryLock(key, waitTime, -1, unit, isFair);
    }

    /**
     * 尝试获取锁，如果锁不可用则等待最多waitTime时间后放弃(需要手动释放且非公平)
     *
     * @param key        锁的key
     * @param waitTime   获取锁的最大尝试时间
     * @param unit       加锁的时间，超过这个时间后锁便自动解锁；
     * @return           锁对象，如果获取锁失败则为null
     */
    default SysLock tryLock(String key, long waitTime, TimeUnit unit) {
        return this.tryLock(key, waitTime, -1, unit, false);
    }

    /**
     * 释放锁
     *
     * @param lock 锁对象
     */
    void unlock(Object lock);

    /**
     * 释放锁
     *
     * @param sysLock {@link SysLock} 抽象锁对象
     */
    default void unlock(SysLock sysLock) {
        if (sysLock != null) {
            this.unlock(sysLock.getLock());
        }
    }
}
