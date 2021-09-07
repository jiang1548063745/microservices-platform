package org.xiaoxianyu.common.log.trace;

import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.xiaoxianyu.common.log.properties.TraceProperties;

import javax.annotation.Resource;

/**
 * feign拦截器，传递traceId
 *
 * @author rorschach
 * @date 2021/9/7 15:30
 */
@ConditionalOnClass(value = {RequestInterceptor.class})
public class FeignTraceInterceptor {

    @Resource
    private TraceProperties traceProperties;

    @Bean
    public RequestInterceptor feignTraceInterceptor() {
        return template -> {
            if (traceProperties.getEnable()) {
                //传递日志traceId
                String traceId = MdcTraceUtils.getTraceId();
                if (!StringUtils.isEmpty(traceId)) {
                    template.header(MdcTraceUtils.TRACE_ID_HEADER, traceId);
                }
            }
        };
    }
}
