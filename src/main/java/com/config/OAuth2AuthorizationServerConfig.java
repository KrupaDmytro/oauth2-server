package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;


@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private String clientid = "tutorialspoint";
    private String clientSecret = "my-secret-key";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAyHZsp7JTQy/Ts9/k2IyqHn1hEcxAK/Y/28LjaiOcRhpv1aH8\n" +
            "6qhw3ZyzGAHPE5qmcTljnrK+i+eXADU/pNZ1IBF1CyWCxo2whb1Q+hdxARLeBXPG\n" +
            "oYe9qChjEq7JBxxQ1T9RHcbn36wHTPVUvn61zzaFcndq93uqlAkao5lFUR+HuaFe\n" +
            "56wPEdUjz9/UkyMnIP+wlB6025nr/OH8dpLrJde60xyT8lxZlcDmiBscP0+CRcQL\n" +
            "sj4nXqxSz3nodD8Nd17GmwDGdSOh2Gkm3wz7igCn6s9ZmsZadryIrjBINLY/HVPf\n" +
            "v3SefCbmT2q9RAoswTZUsZidFnbVKIXwv4R+BwIDAQABAoIBAQDHPGxhf+shK7fN\n" +
            "Xwmj8KpkA1kmx0pAZ06wrNI5+4qmYkAkpAsrbp4+pC+b/LBDW8FxZwiMAjs/8b0y\n" +
            "h7npqvxeEvuxSGbh0JFRWwfQiNvXpVjlMlyIztDBAOL2/qDuYEY2q+eDIN30gJYA\n" +
            "9xRzAJzShe28BtRZhJ/U4feIAh3SsTB/osBWaqHSN17Y/cMcH8H4h5V+DY/0bnd9\n" +
            "hycTSrOTnI/RPYvrqEDiUZWabOrDkH9DlwiWPuc0Z9lT20P2w5Df4n3bfchZiElp\n" +
            "V3LVs2XvTAEnj02qiJpEAq9gf0OH0Lu91PTjDmy7M8GAd/86aNWE9g6B5CaYmUcL\n" +
            "ekLMteWBAoGBAPHWr8Y/jfbT4mPu6bSw9gTWnVKsVAwuzq6hUTF44MplqDdbksUE\n" +
            "/kTQpevQfjHzuQ5G92abx9UGrDjmRTgdmjqigCdmsp3GdwS2KAxCp9E0URp1rHd6\n" +
            "ZLux70xKsg0055lXS5U8cpyrUHhQ83C9hf/GhGBDtOOJ2qOiUNJck0jbAoGBANQz\n" +
            "fDE9XFE6I5snN0mZeKo2ebRZONbS6F70EG3Rq4+fG7EQ/DSC44i/wCTddYeQ12al\n" +
            "21/tHjtAnICN4WZhDY7XLt7ZxQ6eousKWua+M8F/LnNIrPSjeC02wlf7bPwGVJN6\n" +
            "4iw7r0C5gXyRAsbDGshftDR6BAODbfmqLAHfawFFAoGAJJooqmmuE+CH0DY1uvpZ\n" +
            "kevljcC1S869y2JxBnrUEu4F4rMCaL1TupiVtDYvE2Je7NFC2o3TVeOXp1j6uv6H\n" +
            "/D3iBZSejhCerODg+NIR0jEH4WhT/RVSL7JDlJltj2AleWjj8KdgdWPcXwlpfvPA\n" +
            "VzD1khhNRW7033VOpDgMtqcCgYAIxXwLuQKY5PNV1YAmja58MrZDKzD7SEqMqkAH\n" +
            "mhQiYqxNXGtgbEyleW+i1nimOul8d7yisXV/c4NEmRjJF5fs4J4yXgQUP6ByYMIz\n" +
            "KYeQJwavg0CK+Dree5X69wyhOk3CruCsfWcYINLmaEQaHTR9Nd+ID8ccshEYsd0R\n" +
            "0r9klQKBgGsdpIF7JjMlCud0bUV5nHrw6noLA3ptsgyKMmUAwbju/NHbIC7SO9Pa\n" +
            "4T7elWDV6biBrcHoUTAyKFbq4SG17qBvyN9T8rvpezy0o92mEI9XSelUQ7hL2zDv\n" +
            "dOxl43wjs6Lb1iS+TZtSPqmxF9poIt1DjLNiWw4uAjFBsmFNRxP/\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyHZsp7JTQy/Ts9/k2Iyq\n" +
            "Hn1hEcxAK/Y/28LjaiOcRhpv1aH86qhw3ZyzGAHPE5qmcTljnrK+i+eXADU/pNZ1\n" +
            "IBF1CyWCxo2whb1Q+hdxARLeBXPGoYe9qChjEq7JBxxQ1T9RHcbn36wHTPVUvn61\n" +
            "zzaFcndq93uqlAkao5lFUR+HuaFe56wPEdUjz9/UkyMnIP+wlB6025nr/OH8dpLr\n" +
            "Jde60xyT8lxZlcDmiBscP0+CRcQLsj4nXqxSz3nodD8Nd17GmwDGdSOh2Gkm3wz7\n" +
            "igCn6s9ZmsZadryIrjBINLY/HVPfv3SefCbmT2q9RAoswTZUsZidFnbVKIXwv4R+\n" +
            "BwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.user}")
    private String user;
    @Value("${jdbc.pass}")
    private String pass;

    @Autowired
    @Qualifier("encoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(driverClassName)
                .url(url)
                .username(user)
                .password(pass)
                .build();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientid)
                .secret(passwordEncoder.encode(clientSecret))
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }
}