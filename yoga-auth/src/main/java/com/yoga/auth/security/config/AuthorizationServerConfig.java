package com.yoga.auth.security.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.yoga.auth.security.core.clientdetails.ClientDetailsServiceImpl;
import com.yoga.auth.security.core.userdetails.member.MemberUserDetails;
import com.yoga.auth.security.core.userdetails.member.MemberUserDetailsServiceImpl;
import com.yoga.auth.security.core.userdetails.user.SysUserDetails;
import com.yoga.auth.security.core.userdetails.user.SysUserDetailsServiceImpl;
import com.yoga.auth.security.extension.captcha.CaptchaTokenGranter;
import com.yoga.auth.security.extension.refresh.PreAuthenticatedUserDetailsService;
import com.yoga.auth.security.extension.wechat.WechatTokenGranter;
import com.yoga.common.constant.SecurityConstants;
import com.yoga.common.result.Result;
import com.yoga.common.result.ResultCode;
import com.yoga.redis.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.security.KeyPair;
import java.util.*;

/**
 * ??????????????????
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final ClientDetailsServiceImpl clientDetailsService;
    private final SysUserDetailsServiceImpl sysUserDetailsService;
    private final MemberUserDetailsServiceImpl memberUserDetailsService;

    @Autowired
    private RedisService redisService;


    /**
     * OAuth2?????????
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * ???????????????authorization??????????????????token?????????????????????????????????(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // Token??????
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        // ??????????????????????????????(???????????????????????????????????????????????????????????????)????????????
        List<TokenGranter> granterList = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));

        // ????????????????????????????????????
        granterList.add(new CaptchaTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager, redisService
        ));

        // ????????????????????????????????????
        granterList.add(new WechatTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager
        ));

        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .tokenGranter(compositeTokenGranter)

                .tokenServices(tokenServices(endpoints))
        ;
    }


    public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        // ???????????????????????????token?????????????????????ID??? UserDetailService ?????????Map
        Map<String, UserDetailsService> clientUserDetailsServiceMap = new HashMap<>();
        clientUserDetailsServiceMap.put(SecurityConstants.ADMIN_CLIENT_ID, sysUserDetailsService); // ?????????????????????
        clientUserDetailsServiceMap.put(SecurityConstants.WEAPP_CLIENT_ID, memberUserDetailsService); // ????????????????????????

        // ??????token?????????????????????????????????????????????AuthenticationManager??????????????????????????????ID?????????????????????????????????????????????????????????
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(clientUserDetailsServiceMap));
        tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));

        /** refresh_token????????????????????????????????????(true)??????????????????(false)????????????true
         *  1 ???????????????access_token?????????????????? refresh_token?????????????????????????????????????????????????????????
         *  2 ??????????????????access_token?????????????????? refresh_token????????????????????????refresh_token??????????????????????????????????????????????????????????????????
         */
        tokenServices.setReuseRefreshToken(true);
        return tokenServices;

    }


    /**
     * ??????????????????????????????token??????
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * ???????????????????????????(??????+??????)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair keyPair = factory.getKeyPair("jwt", "123456".toCharArray());
        return keyPair;
    }

    /**
     * JWT????????????
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = MapUtil.newHashMap();
            Object principal = authentication.getUserAuthentication().getPrincipal();
            if (principal instanceof SysUserDetails) {
                SysUserDetails sysUserDetails = (SysUserDetails) principal;
                additionalInfo.put("userId", sysUserDetails.getUserId());
                additionalInfo.put("username", sysUserDetails.getUsername());
                additionalInfo.put("plcaeId", sysUserDetails.getPlcaeId());
                // ??????????????????(username:????????????)
                if (StrUtil.isNotBlank(sysUserDetails.getAuthenticationIdentity())) {
                    additionalInfo.put("authenticationIdentity", sysUserDetails.getAuthenticationIdentity());
                }
            } else if (principal instanceof MemberUserDetails) {
                MemberUserDetails memberUserDetails = (MemberUserDetails) principal;
                additionalInfo.put("memberId", memberUserDetails.getMemberId());
                additionalInfo.put("username", memberUserDetails.getUsername());
                // ??????????????????(mobile:????????????openId:???????????????????????????????????????)
                if (StrUtil.isNotBlank(memberUserDetails.getAuthenticationIdentity())) {
                    additionalInfo.put("authenticationIdentity", memberUserDetails.getAuthenticationIdentity());
                }
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }


    /**
     * ?????????????????????????????????
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            Result result = Result.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }
}

