package org.xiaoxianyu.common.core.utils;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 这是{@link ThreadPoolTaskExecutor}的一个简单替换，可搭配TransmittableThreadLocal实现父子线程之间的数据传递
 *
 * @author rorschach
 * @date 2021/9/7 16:47
 */
public class CustomThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private static final long serialVersionUID = -5887035957049288777L;

    @Override
    public void execute(@NonNull Runnable task) {
        Runnable ttRunnable = TtlRunnable.get(task);
        if (null != ttRunnable) {
            super.execute(ttRunnable);
        }
    }

    @Override
    public <T> Future<T> submit(@NonNull Callable<T> task) {
        Callable<T> ttlCallable = TtlCallable.get(task);
        return super.submit(ttlCallable);
    }

    @Override
    public Future<?> submit(@NonNull Runnable task) {
        Runnable ttlRunnable = TtlRunnable.get(task);
        return super.submit(ttlRunnable);
    }

    @Override
    public ListenableFuture<?> submitListenable(@NonNull Runnable task) {
        Runnable ttlRunnable = TtlRunnable.get(task);
        return super.submitListenable(ttlRunnable);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(@NonNull Callable<T> task) {
        Callable<T> ttlCallable = TtlCallable.get(task);
        return super.submitListenable(ttlCallable);
    }
}
