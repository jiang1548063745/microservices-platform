package org.xiaoxianyu.commons.oauth2.store;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;
import org.xiaoxianyu.commons.core.constant.SecurityConstants;
import org.xiaoxianyu.commons.oauth2.converter.CustomUserAuthenticationConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author rorschach
 * @date 2021/9/14 16:41
 */
@Configuration
@ConditionalOnProperty(prefix = "xy.oauth2.token.store", name = "type", havingValue = "resJwt")
public class ResJwtTokenStore {

    @Autowired
    private ResourceServerProperties resource;

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        DefaultAccessTokenConverter tokenConverter = (DefaultAccessTokenConverter)converter.getAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        return converter;
    }

    /**
     * ??????????????????????????? Key
     * @return ?????? Key
     */
    private String getPubKey() {
        Resource res = new ClassPathResource(SecurityConstants.RSA_PUBLIC_KEY);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return getKeyFromAuthorizationServer();
        }
    }

    /**
     * ?????????????????????????????????????????????????????? Key
     * @return ?????? Key
     */
    private String getKeyFromAuthorizationServer() {
        if (StrUtil.isNotEmpty(this.resource.getJwt().getKeyUri())) {
            final HttpHeaders headers = new HttpHeaders();
            final String username = this.resource.getClientId();
            final String password = this.resource.getClientSecret();
            if (username != null && password != null) {
                final byte[] token = Base64.getEncoder().encode((username + ":" + password).getBytes());
                headers.add("Authorization", "Basic " + new String(token));
            }
            final HttpEntity<Void> request = new HttpEntity<>(headers);
            final String url = this.resource.getJwt().getKeyUri();
            return (String) Objects.requireNonNull(new RestTemplate()
                            .exchange(url, HttpMethod.GET, request, Map.class).getBody())
                    .get("value");
        }
        return null;
    }
}
