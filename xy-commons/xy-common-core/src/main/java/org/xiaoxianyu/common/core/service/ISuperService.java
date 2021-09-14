package org.xiaoxianyu.common.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaoxianyu.common.core.lock.DistributedLock;

/**
 * service接口父类
 *
 * @author rorschach
 * @date 2021/9/14 9:50
 */
public interface ISuperService<T> extends IService<T> {

    /**
     * 幂等性新增记录
     * 例:
     *      String username = sysUser.getUsername();
     *      boolean result = super.saveIdempotency(sysUser,
     *                          lock, LOCK_KEY_USERNAME+username,
     *                          new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param entity       实体对象
     * @param locker       {@link DistributedLock} 锁实例
     * @param lockKey      锁的Key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已经存在消息提示
     * @return 保存回执
     */
    boolean saveIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param locker       {@link DistributedLock} 锁实例
     * @param lockKey      锁的Key
     * @param countWrapper 判断是否存在的条件
     * @return 保存回执
     */
    boolean saveIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper);

    /**
     * 幂等性新增或更新记录
     * 例:
     *      String username = sysUser.getUsername();
     *      boolean result = super.saveOrUpdateIdempotency(sysUser, lock, LOCK_KEY_USERNAME+username,
     *                                  new QueryWrapper<SysUser>().eq("username", username)
     *
     * @param entity       实体对象
     * @param locker       锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return             新增或更新回执
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param locker       锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return             新增或更新回执
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper);
}
