package com.emerchantpay.emerchantpaypaymentsystem.security.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

  @Value("${application.jwt.secretKey}")
  private String secretKey;

  @Value("${application.jwt.tokenPrefix}")
  private String tokenPrefix;

  @Value("${application.jwt.tokenExpirationAfterDays}")
  private Integer tokenExpirationAfterDays;

  public JwtConfig() {
  }

  public SecretKey getSecretKeyAsSecretKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getTokenPrefix() {
    return tokenPrefix;
  }

  public void setTokenPrefix(String tokenPrefix) {
    this.tokenPrefix = tokenPrefix;
  }

  public Integer getTokenExpirationAfterDays() {
    return tokenExpirationAfterDays;
  }

  public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
    this.tokenExpirationAfterDays = tokenExpirationAfterDays;
  }

  public String getAuthorizationHeader() {
    return HttpHeaders.AUTHORIZATION;
  }
}
