package org.xiaoxianyu.common.core.lock;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.xiaoxianyu.common.core.exception.LockException;

import java.util.Objects;

/**
 * 分布式锁切面
 *
 * @author rorschach
 * @date 2021/9/14 10:13
 */
@Slf4j
@Aspect
public class LockAspect {

    @Autowired(required = false)
    private DistributedLock locker;

    /**
     * 用于SpEL表达式解析
     */
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    private final static String EL_SPARATE = "#";

    /**
     * 切面环绕
     *
     * @param point {@link ProceedingJoinPoint}
     * @param lock  {@link Lock}
     * @return  执行结果
     */
    @Around("@within(lock) || @annotation(lock)")
    public Object aroundLock(ProceedingJoinPoint point, Lock lock) throws Throwable {
        if (lock == null) {
            // 获取类上的注解
            lock = point.getTarget().getClass().getDeclaredAnnotation(Lock.class);
        }

        String lockKey = lock.key();
        if (locker == null) {
            throw new LockException("DistributedLock is null");
        }
        if (StrUtil.isEmpty(lockKey)) {
            throw new LockException("lockKey is null");
        }
        if (lockKey.contains(EL_SPARATE)) {
            MethodSignature methodSignature = (MethodSignature)point.getSignature();
            //获取方法参数值
            Object[] args = point.getArgs();
            lockKey = getValBySpEl(lockKey, methodSignature, args);
        }

        SysLock sysLock = null;

        try {
            //加锁
            if (lock.waitTime() > 0) {
                sysLock = locker.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.unit(), lock.isFair());
            } else {
                sysLock = locker.lock(lockKey, lock.leaseTime(), lock.unit(), lock.isFair());
            }

            if (null != sysLock) {
                return point.proceed();
            } else {
                throw new LockException("锁等待超时");
            }
        } finally {
            locker.unlock(sysLock);
        }
    }

    /**
     * 解析spEL表达式
     *
     * @param springEl         Spring EL表达式
     * @param methodSignature  {@link MethodSignature}
     * @param args             参数
     * @return                 解析结果
     */
    private String getValBySpEl(String springEl, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());

        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(springEl);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for(int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }

            return Objects.requireNonNull(expression.getValue(context)).toString();
        }
        return null;
    }
}
