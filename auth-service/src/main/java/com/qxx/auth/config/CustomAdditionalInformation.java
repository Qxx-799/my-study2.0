package com.qxx.auth.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomAdditionalInformation implements TokenEnhancer {

    // jwt默认生成的用户信息主要是用户角色、用户名等，如果我们希望在生成的jwt上面添加一些额外信息，就可以按照如下方式添加
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = oAuth2AccessToken.getAdditionalInformation();
        info.put("author", "qxx");
        ((DefaultOAuth2AccessToken) oAuth2AccessToken ).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
