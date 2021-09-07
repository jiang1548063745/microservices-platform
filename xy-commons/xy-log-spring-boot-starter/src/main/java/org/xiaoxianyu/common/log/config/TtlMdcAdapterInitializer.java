package org.xiaoxianyu.common.log.config;

import org.slf4j.TtlMdcAdapter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 初始化TtlMdcAdapter实例，并替换MDC中的adapter对象
 *
 * @author rorschach
 * @date 2021/9/7 11:53
 */
public class TtlMdcAdapterInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        // 加载TtlMdcAdapter实例
        TtlMdcAdapter.getInstance();
    }
}
