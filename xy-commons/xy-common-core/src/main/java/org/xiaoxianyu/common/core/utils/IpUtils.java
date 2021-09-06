package org.xiaoxianyu.common.core.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 *
 * @author 15480
 * @date 2021/09/06 23:02
 */
//@Sl4j
public class IpUtils {

    private final static String UNKNOWN_STR = "unknown";

    /**
     * 获取客户端IP地址
     *
     * @param request {@link HttpServletRequest}
     * @return Ip地址
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (isEmptyIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (isEmptyIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (isEmptyIp(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    if (isEmptyIp(ip)) {
                        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                        if (isEmptyIp(ip)) {
                            ip = request.getRemoteAddr();
                            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                                // 根据网卡取本机配置的IP
                                ip = getLocalIp();
                            }
                        }
                    }
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String strIp : ips) {
                if (!isEmptyIp(ip)) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 判断是否为空白IP
     *
     * @param ip Ip
     * @return 是否为空白IP
     */
    private static boolean isEmptyIp(String ip) {
        return StrUtil.isEmpty(ip) || UNKNOWN_STR.equalsIgnoreCase(ip);
    }

    /**
     * 获取本机的IP地址
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
//            log.error("InetAddress.getLocalHost()-error", e);
        }

        return "";
    }
}
