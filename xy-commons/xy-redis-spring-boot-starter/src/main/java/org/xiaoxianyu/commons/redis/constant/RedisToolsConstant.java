package org.xiaoxianyu.commons.redis.constant;

/**
 * redis 工具常量
 *
 * @author rorschach
 * @date 2021/9/14 15:33
 */
public class RedisToolsConstant {

    private RedisToolsConstant() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * single Redis
     */
    public final static int SINGLE = 1 ;

    /**
     * Redis cluster
     */
    public final static int CLUSTER = 2 ;
}
