package com.wqy.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 用户认证 Manager
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 数据源 DataSource
     */
    @Autowired
    private DataSource dataSource;

  /*  @Autowired
    private UserDetailsService userDetailsService;*/

    @Bean
    public TokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }

//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService);
//    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(jdbcTokenStore());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("isAuthenticated()");
    }

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory() // <4.1>
//                .withClient("clientapp").secret("112233") // <4.2> Client 账号、密码。
//                .authorizedGrantTypes("password","refresh_token") // <4.2> 密码模式
//                .scopes("read_userinfo", "read_contacts") // <4.2> 可授权的 Scope
////                .and().withClient() // <4.3> 可以继续配置新的 Client
//                .accessTokenValiditySeconds(36)
//                .refreshTokenValiditySeconds(86400)
//                ;
//    }

    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

}