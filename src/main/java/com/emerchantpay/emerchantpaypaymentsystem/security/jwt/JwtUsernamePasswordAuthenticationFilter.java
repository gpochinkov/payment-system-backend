package com.emerchantpay.emerchantpaypaymentsystem.security.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  private JwtConfig jwtConfig;

  public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                 JwtConfig jwtConfig) {
    this.authenticationManager = authenticationManager;
    this.jwtConfig = jwtConfig;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response)
      throws AuthenticationException {

    try {
      UsernamePasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
          .readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);

      Authentication authentication = new UsernamePasswordAuthenticationToken(
          authenticationRequest.getUsername(),
          authenticationRequest.getPassword()
      );

      Authentication authenticate = authenticationManager.authenticate(authentication);
      return authenticate;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult)
      throws IOException, ServletException {
    String token = Jwts.builder()
                       .setSubject(authResult.getName())
                       .claim("authorities", authResult.getAuthorities())
                       .setIssuedAt(new Date())
                       .setExpiration(Date.from(
                           LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())
                                    .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                       .signWith(jwtConfig.getSecretKeyAsSecretKey())
                       .compact();

    response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
  }
}
