package org.xiaoxianyu.commons.core.enums;

/**
 * 返回码枚举
 *
 * @author rorschach
 * @date 2021/9/14 10:32
 */
public enum ResultEnum {

    /**
     * 状态枚举
     */
    SUCCESS("00000"),
    ERROR("10000");

    private final String code;

    ResultEnum(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
