package org.xiaoxianyu.commons.oauth2.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.xiaoxianyu.commons.core.constant.SecurityConstants;
import org.xiaoxianyu.commons.core.utils.JsonUtil;
import org.xiaoxianyu.commons.core.utils.RsaUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.stream.Collectors;

/**
 * jwt工具类
 *
 * @author rorschach
 * @date 2021/9/14 16:06
 */
public class JwtUtils {

    private static final String PUBLIC_KEY_START = "-----BEGIN PUBLIC KEY-----";
    private static final String PUBLIC_KEY_END = "-----END PUBLIC KEY-----";

    /**
     * 通过classpath获取公钥值
     */
    public static RSAPublicKey getPubKeyObj() {
        Resource res = new ClassPathResource(SecurityConstants.RSA_PUBLIC_KEY);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()))) {
            String pubKey = br.lines().collect(Collectors.joining("\n"));
            pubKey = pubKey.substring(PUBLIC_KEY_START.length(), pubKey.indexOf(PUBLIC_KEY_END));
            return RsaUtils.getPublicKey(pubKey);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * {"exp":1563256084,"user_name":"admin","authorities":["ADMIN"],"jti":"4ce02f54-3d1c-4461-8af1-73f0841a35df","client_id":"webApp","scope":["app"]}
     *
     * @param jwtToken token值
     * @param rsaPublicKey 公钥
     * @return {@link JsonNode}
     */
    public static JsonNode decodeAndVerify(String jwtToken, RSAPublicKey rsaPublicKey) {
        SignatureVerifier rsaVerifier = new RsaVerifier(rsaPublicKey);
        Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, rsaVerifier);
        return JsonUtil.parse(jwt.getClaims());
    }

    /**
     * {"exp":1563256084,"user_name":"admin","authorities":["ADMIN"],"jti":"4ce02f54-3d1c-4461-8af1-73f0841a35df","client_id":"webApp","scope":["app"]}
     *
     * @param jwtToken token值
     * @return {@link JsonNode}
     */
    public static JsonNode decodeAndVerify(String jwtToken) {
        return decodeAndVerify(jwtToken, getPubKeyObj());
    }

    /**
     * 判断jwt是否过期
     *
     * @param claims jwt内容
     * @param currTime 当前时间
     * @return 未过期：true，已过期：false
     */
    public static boolean checkExp(JsonNode claims, long currTime) {
        long exp = claims.get("exp").asLong();
        return exp >= currTime;
    }

    /**
     * 判断jwt是否过期
     *
     * @param claims jwt内容
     * @return 未过期：true，已过期：false
     */
    public static boolean checkExp(JsonNode claims) {
        return checkExp(claims, System.currentTimeMillis());
    }

    public static Jwt encode(CharSequence content, KeyProperties keyProperties) {
        KeyPair keyPair = new KeyStoreKeyFactory(
                keyProperties.getKeyStore().getLocation(),
                keyProperties.getKeyStore().getSecret().toCharArray())
                .getKeyPair(keyProperties.getKeyStore().getAlias());
        PrivateKey privateKey = keyPair.getPrivate();
        Signer rsaSigner = new RsaSigner((RSAPrivateKey) privateKey);
        return JwtHelper.encode(content, rsaSigner);
    }

    public static String encodeStr(CharSequence content, KeyProperties keyProperties) {
        return encode(content, keyProperties).getEncoded();
    }
}
