package org.xiaoxianyu.common.core.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * PasswordEncoder实现工具类
 *
 * @author rorschach
 * @date 2021/9/14 9:33
 */
public class PwdEncoderUtil {

    /**
     * 获取密码加密器
     *
     * @param encodingId 加密ID
     * @return {@link PasswordEncoder}
     */
    public static PasswordEncoder getDelegatingPasswordEncoder(String encodingId) {
        Map<String, PasswordEncoder> encoders = new HashMap<>(16);
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("MD4", new Md4PasswordEncoder());
        encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());

        Assert.isTrue(encoders.containsKey(encodingId), encodingId + " is not found in idToPasswordEncoder");

        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(encoders.get(encodingId));
        return delegatingPasswordEncoder;
    }
}
