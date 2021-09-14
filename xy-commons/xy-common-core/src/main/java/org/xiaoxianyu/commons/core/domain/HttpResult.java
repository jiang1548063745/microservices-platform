package org.xiaoxianyu.commons.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xiaoxianyu.commons.core.enums.ResultEnum;

import java.io.Serializable;

/**
 * 消息报文处理
 *
 * @author rorschach
 * @date 2021/9/14 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult<T> implements Serializable {

    private T data;

    private String code;

    private String msg;

    /**
     * 成功报文(消息)
     *
     * @param msg 消息
     * @param <T> 数据泛型
     * @return {@link HttpResult}
     */
    public static <T> HttpResult<T> success(String msg) {
        return of(null, ResultEnum.SUCCESS.getCode(), msg);
    }

    /**
     * 成功报文(消息+数据)
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  数据泛型
     * @return {@link HttpResult}
     */
    public static <T> HttpResult<T> success(T data, String msg) {
        return of(data, ResultEnum.SUCCESS.getCode(), msg);
    }

    /**
     * 成功报文(数据)
     *
     * @param data 数据
     * @param <T>  数据泛型
     * @return {@link HttpResult}
     */
    public static <T> HttpResult<T> success(T data) {
        return of(data, ResultEnum.SUCCESS.getCode(), null);
    }

    /**
     * 获取返回消息报文
     *
     * @param data 报文数据
     * @param code 报文状态码
     * @param msg  消息
     * @param <T>  数据泛型
     * @return  {@link HttpResult}
     */
    public static <T> HttpResult<T> of(T data, String code, String msg) {
        return new HttpResult<>(data, code, msg);
    }

    /**
     * 失败报文(数据+消息)
     *
     * @param msg 消息
     * @param <T> 泛型
     * @return {@link HttpResult}
     */
    public static <T> HttpResult<T> failed(String msg) {
        return of(null, ResultEnum.ERROR.getCode(), msg);
    }

    /**
     * 失败报文(数据+消息)
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  数据泛型
     * @return {@link HttpResult}
     */
    public static <T> HttpResult<T> failed(T data, String msg) {
        return of(data, ResultEnum.ERROR.getCode(), msg);
    }
}
