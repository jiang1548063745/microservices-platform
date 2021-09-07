package org.xiaoxianyu.common.log.trace;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.xiaoxianyu.common.log.properties.TraceProperties;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web过滤器，生成日志链路追踪id，并赋值MDC
 *
 * @author rorschach
 * @date 2021/9/7 15:32
 */
@ConditionalOnClass(value = {HttpServletRequest.class, OncePerRequestFilter.class})
@Order(value = MdcTraceUtils.FILTER_ORDER)
public class WebTraceFilter extends OncePerRequestFilter {

    @Resource
    private TraceProperties traceProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !traceProperties.getEnable();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = request.getHeader(MdcTraceUtils.TRACE_ID_HEADER);
            if (StringUtils.isEmpty(traceId)) {
                MdcTraceUtils.addTraceId();
            } else {
                MdcTraceUtils.putTraceId(traceId);
            }
            filterChain.doFilter(request, response);
        } finally {
            MdcTraceUtils.removeTraceId();
        }
    }
}
