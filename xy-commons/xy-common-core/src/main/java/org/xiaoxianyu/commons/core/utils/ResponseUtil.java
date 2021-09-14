package org.xiaoxianyu.commons.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.xiaoxianyu.commons.core.domain.HttpResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Response 工具类
 *
 * @author rorschach
 * @date 2021/9/14 15:22
 */
public class ResponseUtil {

    private ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 通过流写到前端
     *
     * @param objectMapper 对象序列化
     * @param response     {@link HttpServletResponse}
     * @param msg          返回信息
     * @param httpStatus   返回状态码
     */
    public static void responseWriter(ObjectMapper objectMapper, HttpServletResponse response, String msg, String httpStatus) throws IOException {
        HttpResult<Void> result = HttpResult.of(null, httpStatus, msg);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 通过流写到前端
     *
     * @param objectMapper 对象序列化
     * @param response     {@link HttpServletResponse}
     * @param obj          需要写出的对象
     */
    public static <T> void responseSucceed(ObjectMapper objectMapper, HttpServletResponse response, T obj) throws IOException {
        HttpResult<T> result = HttpResult.success(obj);
        responseWrite(objectMapper, response, result);
    }

    /**
     *
     * @param objectMapper
     * @param response
     * @param msg
     * @throws IOException
     */
    /**
     * 通过流写到前端
     *
     * @param objectMapper 对象序列化
     * @param response     {@link HttpServletResponse}
     * @param msg          消息
     */
    public static void responseFailed(ObjectMapper objectMapper, HttpServletResponse response, String msg) throws IOException {
        HttpResult<Void> result = HttpResult.failed(msg);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 响应写出
     *
     * @param objectMapper {@link ObjectMapper}
     * @param response {@link HttpServletResponse}
     * @param result {@link HttpResult}
     */
    private static void responseWrite(ObjectMapper objectMapper, HttpServletResponse response, HttpResult<?> result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
        }
    }
}
