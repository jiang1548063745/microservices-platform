package org.xiaoxianyu.commons.oauth2.token;

import lombok.Getter;

import java.io.Serializable;

/**
 * 表单登录的认证信息对象
 *
 * @author rorschach
 * @date 2021/9/14 16:04
 */
@Getter
public class CustomWebAuthenticationDetails implements Serializable {

    private static final long serialVersionUID = - 1;

    private final String accountType;
    private final String remoteAddress;
    private final String sessionId;

    public CustomWebAuthenticationDetails(String remoteAddress, String sessionId, String accountType) {
        this.remoteAddress = remoteAddress;
        this.sessionId = sessionId;
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return super.toString() + "; accountType: " + this.getAccountType();
    }
}
