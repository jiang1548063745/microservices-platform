package org.xiaoxianyu.common.log.trace;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * dubbo过滤器，传递traceId
 *
 * @author rorschach
 * @date 2021/9/7 15:36
 */
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER}, order = MdcTraceUtils.FILTER_ORDER)
public class DubboTraceFilter implements Filter {

    /**
     * 服务消费者：传递traceId给下游服务
     * 服务提供者：获取traceId并赋值给MDC
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        boolean isProviderSide = RpcContext.getContext().isProviderSide();
        if (isProviderSide) {
            //服务提供者逻辑
            String traceId = invocation.getAttachment(MdcTraceUtils.KEY_TRACE_ID);
            if (StringUtils.isEmpty(traceId)) {
                MdcTraceUtils.addTraceId();
            } else {
                MdcTraceUtils.putTraceId(traceId);
            }
        } else {
            //服务消费者逻辑
            String traceId = MdcTraceUtils.getTraceId();
            if (!StringUtils.isEmpty(traceId)) {
                invocation.setAttachment(MdcTraceUtils.KEY_TRACE_ID, traceId);
            }
        }

        try {
            return invoker.invoke(invocation);
        } finally {
            if (isProviderSide) {
                MdcTraceUtils.removeTraceId();
            }
        }
    }
}
