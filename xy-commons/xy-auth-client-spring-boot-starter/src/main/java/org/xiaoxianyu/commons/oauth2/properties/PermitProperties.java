package org.xiaoxianyu.commons.oauth2.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 配置需要放权的url白名单
 *
 * @author rorschach
 * @date 2021/9/14 16:17
 */
public class PermitProperties {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {
            "/oauth/**",
            "/actuator/**",
            "/*/v2/api-docs",
            "/swagger/api-docs",
            "/swagger-ui.html",
            "/doc.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/druid/**"
    };

    /**
     * 设置不用认证的url
     */
    private final String[] httpUrls = {};

    public String[] getUrls() {
        if (httpUrls == null || httpUrls.length == 0) {
            return ENDPOINTS;
        }
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(ENDPOINTS));
        list.addAll(Arrays.asList(httpUrls));
        return list.toArray(new String[0]);
    }
}
