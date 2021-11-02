package com.qxx.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务器配置类
 */
@EnableAuthorizationServer //表示开启授权服务器的自动化配置
@Configuration
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    /**
     * 指定Token的存储位置
     */
    @Autowired
    TokenStore tokenStore;

    @Autowired
    ClientDetailsService clientDetailsService;

    /**
     * 用来配置token的一些基本信息，例如token是否支持刷新、token的存储位置、有效期等
     */
    @Bean
    AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(60 * 60 * 2);
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return services;
    }



    /**
     * 用来配置令牌端点的安全约束，也就是谁能访问，谁不能访问。
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()") // 指定一个token校验的端点， 这里设置了可以直接访问 （资源服务器接收到token之后，需要校验token的合法性，就会访问这个端点）
                .allowFormAuthenticationForClients();
    }

    /**
     * 用来配置客户端的详细信息
     * （授权服务器要做两方面校验，一个是校验客户端，一个是校验用户，校验用户的已经在Security中配置了）这里就是配置校验的客户端
     * @param clients
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("authService")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .resourceIds("auth")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .redirectUris("http://localhost:8083/index.html");
    }

    /**
     * 用来配置令牌的访问端点和令牌服务
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices()) // 配置授权码的存储 这里放到了内存中
                .tokenServices(tokenServices()); // 用来配置令牌的存储，即AccessToken的存储位置，这里也先放到内存中
    }

    @Bean
    AuthorizationCodeServices authorizationCodeServices(){
        return new InMemoryAuthorizationCodeServices();
    }
}
